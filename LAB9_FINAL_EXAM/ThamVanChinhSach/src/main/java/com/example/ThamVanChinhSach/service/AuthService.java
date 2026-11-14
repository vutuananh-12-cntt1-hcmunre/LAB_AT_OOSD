package com.example.ThamVanChinhSach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.LoginRequest;
import com.example.ThamVanChinhSach.dto.RegisterRequest;
import com.example.ThamVanChinhSach.repository.AuthRepository;
import com.example.ThamVanChinhSach.repository.LoginResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String dangKy(RegisterRequest request) {
        try {
            Integer maNguoiDungMoi = authRepository.dangKyNguoiDung(request, request.getMatKhau());
            return "Đăng ký thành công. Mã người dùng mới: " + maNguoiDungMoi;
        } catch (RuntimeException e) {
            // AuthRepository đã làm sạch thông báo
            return "Lỗi: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi: Đăng ký thất bại. Vui lòng thử lại.";
        }
    }

    public LoginResponse dangNhap(LoginRequest request) {
        // AuthRepository ném RuntimeException với message tiếng Việt sạch
        LoginResponse response = authRepository.dangNhap(request.getEmail(), request.getMatKhau());
        String token = jwtTokenProvider.generateToken(
                response.getMaNguoiDung(),
                response.getEmail(),
                response.getMaVaiTro()
        );
        response.setToken(token);
        return response;
    }
}