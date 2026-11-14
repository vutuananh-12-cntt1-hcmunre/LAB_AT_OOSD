package com.example.ThamVanChinhSach.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.TaiLieuService;

/**
 * Kiểm thử phân quyền và hợp đồng HTTP cho tài liệu đính kèm (FR03/FR17).
 */
@ExtendWith(MockitoExtension.class)
class TaiLieuControllerTest {

    @Mock
    private TaiLieuService taiLieuService;

    private MockMvc mockMvc;
    private JwtTokenProvider jwt;

    @BeforeEach
    void setUp() {
        jwt = new JwtTokenProvider();
        TaiLieuController controller = new TaiLieuController(taiLieuService, jwt);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void upload_invalidToken_returns401() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "data".getBytes());
        mockMvc.perform(multipart("/api/tailieu/upload/11")
                        .file(file)
                        .header("Authorization", "Bearer token-sai"))
                .andExpect(status().isUnauthorized());
        verify(taiLieuService, never()).luuFileTaiLieu(anyInt(), any());
    }

    @Test
    void xoaTaiLieu_invalidToken_returns401() throws Exception {
        mockMvc.perform(delete("/api/tailieu/5")
                        .header("Authorization", "Bearer token-sai"))
                .andExpect(status().isUnauthorized());
        verify(taiLieuService, never()).xoaTaiLieu(5);
    }

    @Test
    void xoaTaiLieu_citizenRole_returns403() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");
        mockMvc.perform(delete("/api/tailieu/5")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
        verify(taiLieuService, never()).xoaTaiLieu(5);
    }

    @Test
    void xoaTaiLieu_officer_notFound_returns400() throws Exception {
        String token = jwt.generateToken(9, "canbo@gov.vn", "CAN_BO_CO_QUAN");
        when(taiLieuService.xoaTaiLieu(5)).thenReturn("Lỗi: Không tìm thấy tài liệu ID: 5");
        mockMvc.perform(delete("/api/tailieu/5")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void layTheoYKien_invalidToken_returns401() throws Exception {
        mockMvc.perform(get("/api/tailieu/theo-ykien/11")
                        .header("Authorization", "Bearer token-sai"))
                .andExpect(status().isUnauthorized());
        verify(taiLieuService, never()).layTheoYKien(11);
    }
}
