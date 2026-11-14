package com.example.ThamVanChinhSach.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.ThamVanChinhSach.dto.DeXuatNhomResponse;
import com.example.ThamVanChinhSach.dto.DuThaoTiepThuResponse;
import com.example.ThamVanChinhSach.dto.RaSoatRequest;
import com.example.ThamVanChinhSach.service.DuThaoService;
import com.example.ThamVanChinhSach.service.TruyVetService;
import com.example.ThamVanChinhSach.service.YKienService;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Kiểm thử tích hợp HTTP ở lớp controller (không cần SQL Server).
 * JWT thật được ký bằng JwtTokenProvider; service được cô lập để xác nhận
 * hợp đồng HTTP, phân quyền và việc không bỏ qua bước rà soát của con người.
 */
@ExtendWith(MockitoExtension.class)
class YKienControllerIntegrationTest {

    @Mock
    private YKienService yKienService;

    @Mock
    private TruyVetService truyVetService;

    @Mock
    private DuThaoService duThaoService;

    private MockMvc mockMvc;
    private JwtTokenProvider jwt;

    @BeforeEach
    void setUp() {
        jwt = new JwtTokenProvider();
        YKienController controller = new YKienController();
        ReflectionTestUtils.setField(controller, "yKienService", yKienService);
        ReflectionTestUtils.setField(controller, "truyVetService", truyVetService);
        ReflectionTestUtils.setField(controller, "jwtTokenProvider", jwt);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void deXuatNhom_requiresAuthentication() throws Exception {
        mockMvc.perform(post("/api/ykien/11/de-xuat-nhom"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("token")));
        verify(yKienService, never()).deXuatNhom(11);
    }

    @Test
    void deXuatNhom_rejectsCitizenRole() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");

        mockMvc.perform(post("/api/ykien/11/de-xuat-nhom")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("quy")));
        verify(yKienService, never()).deXuatNhom(11);
    }

    @Test
    void deXuatNhom_returnsSuggestionMarkedForHumanReview() throws Exception {
        String token = jwt.generateToken(9, "reviewer@example.test", "RA_SOAT");
        when(yKienService.deXuatNhom(11)).thenReturn(new DeXuatNhomResponse(
                11, 3, "Môi trường", 0.8, List.of("môi trường"),
                "Đây chỉ là đề xuất; cán bộ phải rà soát."));

        mockMvc.perform(post("/api/ykien/11/de-xuat-nhom")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maYKien").value(11))
                .andExpect(jsonPath("$.maNhomDeXuat").value(3))
                .andExpect(jsonPath("$.batBuocRaSoat").value(true))
                .andExpect(jsonPath("$.ghiChu").value(containsString("rà soát")));
        verify(yKienService).deXuatNhom(11);
    }

    @Test
    void raSoat_rejectsCitizenBeforeCallingService() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");

        mockMvc.perform(put("/api/ykien/11/ra-soat")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maNhomChinhThuc\":3}"))
                .andExpect(status().isForbidden());
        verify(yKienService, never()).raSoatYKien(any(Integer.class), any(RaSoatRequest.class), any(Integer.class));
    }

    @Test
    void raSoat_allowsReviewerAndPassesAuthenticatedUser() throws Exception {
        String token = jwt.generateToken(9, "reviewer@example.test", "RA_SOAT");
        when(yKienService.raSoatYKien(any(Integer.class), any(RaSoatRequest.class), any(Integer.class)))
                .thenReturn("Cán bộ rà soát phân nhóm thành công cho ý kiến ID: 11");

        mockMvc.perform(put("/api/ykien/11/ra-soat")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maNhomChinhThuc\":3}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("thành công")));
        verify(yKienService).raSoatYKien(any(Integer.class), any(RaSoatRequest.class), org.mockito.ArgumentMatchers.eq(9));
    }

    @Test
    void phanHoi_rejectsReviewerBecauseOnlyOfficerOrAdminMayRespond() throws Exception {
        String token = jwt.generateToken(9, "reviewer@example.test", "RA_SOAT");

        mockMvc.perform(put("/api/ykien/11/phan-hoi")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ketQuaXuLy\":\"Tiếp thu\",\"noiDungGiaiThich\":\"Đã sửa\"}"))
                .andExpect(status().isForbidden());
        verify(yKienService, never()).phanHoiYKien(any(Integer.class), any(String.class), any(String.class), any(Integer.class));
    }

    @Test
    void yKienTrace_returnsTwoWayDraftLinksForAuthenticatedUser() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");
        DuThaoTiepThuResponse draft = new DuThaoTiepThuResponse();
        draft.setMaDuThao(21);
        draft.setTenDuThao("Dự thảo môi trường");
        draft.setPhienBan("v2");
        draft.setNoiDungThayDoi("Bổ sung mục 3");
        when(truyVetService.layDuThaoTiepThuYKien(11)).thenReturn(List.of(draft));

        mockMvc.perform(get("/api/ykien/11/du-thao-lien-ket")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maDuThao").value(21))
                .andExpect(jsonPath("$[0].noiDungThayDoi").value("Bổ sung mục 3"));
        verify(truyVetService).layDuThaoTiepThuYKien(11);
    }

    @Test
    void draftTrace_requiresValidToken() throws Exception {
        DuThaoController draftController = new DuThaoController(duThaoService, truyVetService, jwt);
        MockMvc draftMockMvc = MockMvcBuilders.standaloneSetup(draftController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        draftMockMvc.perform(get("/api/duthao/21/y-kien-lien-ket"))
                .andExpect(status().isUnauthorized());
    }
}
