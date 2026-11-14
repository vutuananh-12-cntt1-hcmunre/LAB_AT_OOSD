package com.example.ThamVanChinhSach.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.ThongKeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/thong-ke")
public class ThongKeController {
    private final ThongKeService thongKeService;
    private final JwtTokenProvider jwtTokenProvider;

    public ThongKeController(ThongKeService thongKeService, JwtTokenProvider jwtTokenProvider) {
        this.thongKeService = thongKeService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/chung")
    public ResponseEntity<?> layThongKe(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }

            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có quyền truy cập thống kê!");
            }

            return ResponseEntity.ok(thongKeService.layThongKeChung());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}