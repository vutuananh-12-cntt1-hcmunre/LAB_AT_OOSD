package com.example.ThamVanChinhSach.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.example.ThamVanChinhSach.dto.NhomYKienRequest;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.NhomYKienService;

/**
 * Kiểm thử phân quyền và hợp đồng HTTP cho CRUD nhóm ý kiến (FR05/FR17).
 */
@ExtendWith(MockitoExtension.class)
class NhomYKienControllerTest {

    @Mock
    private NhomYKienService nhomYKienService;

    private MockMvc mockMvc;
    private JwtTokenProvider jwt;

    @BeforeEach
    void setUp() {
        jwt = new JwtTokenProvider();
        NhomYKienController controller = new NhomYKienController(nhomYKienService, jwt);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void taoNhom_invalidToken_returns401() throws Exception {
        mockMvc.perform(post("/api/nhom-ykien/tao-moi")
                        .header("Authorization", "Bearer token-sai")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maChuDe\":1,\"tenNhom\":\"Nhóm A\"}"))
                .andExpect(status().isUnauthorized());
        verify(nhomYKienService, never()).taoNhom(any(NhomYKienRequest.class));
    }

    @Test
    void taoNhom_citizenRole_returns403() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");
        mockMvc.perform(post("/api/nhom-ykien/tao-moi")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maChuDe\":1,\"tenNhom\":\"Nhóm A\"}"))
                .andExpect(status().isForbidden());
        verify(nhomYKienService, never()).taoNhom(any(NhomYKienRequest.class));
    }

    @Test
    void taoNhom_officer_withEmptyName_returns400() throws Exception {
        String token = jwt.generateToken(9, "canbo@gov.vn", "CAN_BO_CO_QUAN");
        when(nhomYKienService.taoNhom(any(NhomYKienRequest.class)))
                .thenReturn("Lỗi: Tên nhóm không được để trống!");
        mockMvc.perform(post("/api/nhom-ykien/tao-moi")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maChuDe\":1,\"tenNhom\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void capNhatNhom_citizenRole_returns403() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");
        mockMvc.perform(put("/api/nhom-ykien/5")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maChuDe\":1,\"tenNhom\":\"Nhóm A\"}"))
                .andExpect(status().isForbidden());
        verify(nhomYKienService, never()).capNhatNhom(5, null);
    }

    @Test
    void xoaNhom_citizenRole_returns403() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");
        mockMvc.perform(delete("/api/nhom-ykien/5")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
        verify(nhomYKienService, never()).xoaNhom(5);
    }
}
