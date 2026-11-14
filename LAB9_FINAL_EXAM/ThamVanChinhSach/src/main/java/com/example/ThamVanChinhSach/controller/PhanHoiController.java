package com.example.ThamVanChinhSach.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThamVanChinhSach.dto.PhanHoiNhomRequest;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.PhanHoiService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/phan-hoi")
public class PhanHoiController {
    private final PhanHoiService phanHoiService;
    private final JwtTokenProvider jwtTokenProvider;

    public PhanHoiController(PhanHoiService phanHoiService, JwtTokenProvider jwtTokenProvider) {
        this.phanHoiService = phanHoiService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/theo-nhom")
    public ResponseEntity<String> phanHoiTheoNhom(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody PhanHoiNhomRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được phản hồi theo nhóm!");
            }
            int maNguoi = jwtTokenProvider.getUserIdFromToken(token);
            String result = phanHoiService.phanHoiTheoNhom(request, maNguoi);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
