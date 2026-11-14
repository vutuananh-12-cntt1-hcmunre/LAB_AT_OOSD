package com.example.ThamVanChinhSach.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.ThamVanChinhSach.dto.CongBoKetQuaRequest;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.CongBoService;

/**
 * Kiểm thử phân quyền và hợp đồng HTTP cho công bố kết quả (FR09/FR17).
 */
@ExtendWith(MockitoExtension.class)
class CongBoControllerTest {

    @Mock
    private CongBoService congBoService;

    private MockMvc mockMvc;
    private JwtTokenProvider jwt;

    @BeforeEach
    void setUp() {
        jwt = new JwtTokenProvider();
        CongBoController controller = new CongBoController(congBoService, jwt);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void taoCongBo_invalidToken_returns401() throws Exception {
        mockMvc.perform(post("/api/cong-bo/tao-moi")
                        .header("Authorization", "Bearer token-sai")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maChuDe\":1,\"maDuThao\":2,\"noiDungCongBo\":\"Kết quả\"}"))
                .andExpect(status().isUnauthorized());
        verify(congBoService, never()).taoCongBo(any(CongBoKetQuaRequest.class), anyInt());
    }

    @Test
    void taoCongBo_citizenRole_returns403() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");
        mockMvc.perform(post("/api/cong-bo/tao-moi")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maChuDe\":1,\"maDuThao\":2,\"noiDungCongBo\":\"Kết quả\"}"))
                .andExpect(status().isForbidden());
        verify(congBoService, never()).taoCongBo(any(CongBoKetQuaRequest.class), anyInt());
    }

    @Test
    void taoCongBo_officer_withEmptyContent_returns400() throws Exception {
        String token = jwt.generateToken(9, "canbo@gov.vn", "CAN_BO_CO_QUAN");
        when(congBoService.taoCongBo(any(CongBoKetQuaRequest.class), anyInt()))
                .thenReturn("Lỗi: Nội dung công bố không được để trống!");
        mockMvc.perform(post("/api/cong-bo/tao-moi")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maChuDe\":1,\"maDuThao\":2,\"noiDungCongBo\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
