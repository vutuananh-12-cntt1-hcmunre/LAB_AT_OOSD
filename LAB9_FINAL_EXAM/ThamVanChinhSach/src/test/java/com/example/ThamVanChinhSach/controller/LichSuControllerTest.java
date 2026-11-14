package com.example.ThamVanChinhSach.controller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.LichSuService;

/**
 * Kiểm thử phân quyền cho lịch sử xử lý (FR12/FR17).
 */
@ExtendWith(MockitoExtension.class)
class LichSuControllerTest {

    @Mock
    private LichSuService lichSuService;

    private MockMvc mockMvc;
    private JwtTokenProvider jwt;

    @BeforeEach
    void setUp() {
        jwt = new JwtTokenProvider();
        LichSuController controller = new LichSuController(lichSuService, jwt);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void layTheoYKien_invalidToken_returns401() throws Exception {
        mockMvc.perform(get("/api/lich-su/ykien/11")
                        .header("Authorization", "Bearer token-sai"))
                .andExpect(status().isUnauthorized());
        verify(lichSuService, never()).layTheoYKien(11);
    }

    @Test
    void layTheoDuThao_invalidToken_returns401() throws Exception {
        mockMvc.perform(get("/api/lich-su/duthao/21")
                        .header("Authorization", "Bearer token-sai"))
                .andExpect(status().isUnauthorized());
        verify(lichSuService, never()).layTheoDuThao(21);
    }

    @Test
    void layTheoDuThao_citizenRole_returns403() throws Exception {
        String token = jwt.generateToken(8, "dan@example.test", "NGUOI_DAN");
        mockMvc.perform(get("/api/lich-su/duthao/21")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
        verify(lichSuService, never()).layTheoDuThao(21);
    }

    @Test
    void layTheoDuThao_reviewerRole_returns200() throws Exception {
        String token = jwt.generateToken(9, "rasoat@gmail.com", "RA_SOAT");
        when(lichSuService.layTheoDuThao(21)).thenReturn(List.of());
        mockMvc.perform(get("/api/lich-su/duthao/21")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
