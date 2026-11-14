package com.example.ThamVanChinhSach.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThamVanChinhSach.dto.LichSuXuLyResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.LichSuService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lich-su")
public class LichSuController {
    private final LichSuService lichSuService;
    private final JwtTokenProvider jwtTokenProvider;

    public LichSuController(LichSuService lichSuService, JwtTokenProvider jwtTokenProvider) {
        this.lichSuService = lichSuService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/ykien/{maYKien}")
    public ResponseEntity<?> layTheoYKien(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maYKien) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            List<LichSuXuLyResponse> list = lichSuService.layTheoYKien(maYKien);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @GetMapping("/duthao/{maDuThao}")
    public ResponseEntity<?> layTheoDuThao(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maDuThao) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role) && !"RA_SOAT".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có quyền xem lịch sử dự thảo!");
            }
            List<LichSuXuLyResponse> list = lichSuService.layTheoDuThao(maDuThao);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
