package com.example.ThamVanChinhSach.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.ThamVanChinhSach.dto.LoginRequest;
import com.example.ThamVanChinhSach.dto.RegisterRequest;
import com.example.ThamVanChinhSach.repository.LoginResponse;
import com.example.ThamVanChinhSach.service.AuthService;

/**
 * Kiểm thử HTTP tầng controller cho Auth (đăng nhập/đăng ký), không cần CSDL.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        AuthController controller = new AuthController();
        ReflectionTestUtils.setField(controller, "authService", authService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void login_wrongCredentials_returns401() throws Exception {
        when(authService.dangNhap(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Sai email hoặc mật khẩu"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"dan@example.test\",\"matKhau\":\"sai\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_success_returns200WithToken() throws Exception {
        LoginResponse response = new LoginResponse();
        response.setMaNguoiDung(8);
        response.setEmail("dan@example.test");
        response.setMaVaiTro("NGUOI_DAN");
        response.setToken("jwt-token");
        when(authService.dangNhap(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"dan@example.test\",\"matKhau\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void register_missingInfo_returns400() throws Exception {
        when(authService.dangKy(any(RegisterRequest.class)))
                .thenReturn("Lỗi: Vui lòng nhập đầy đủ thông tin!");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"thiếu thông tin\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_duplicateEmail_returns400() throws Exception {
        when(authService.dangKy(any(RegisterRequest.class)))
                .thenReturn("Lỗi: Email đã tồn tại trong hệ thống!");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hoTen\":\"A\",\"email\":\"a@a.vn\",\"matKhau\":\"123456\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_success_returns200() throws Exception {
        when(authService.dangKy(any(RegisterRequest.class)))
                .thenReturn("Đăng ký thành công. Mã người dùng mới: 5");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hoTen\":\"A\",\"email\":\"a@a.vn\",\"matKhau\":\"123456\"}"))
                .andExpect(status().isOk());
    }
}
