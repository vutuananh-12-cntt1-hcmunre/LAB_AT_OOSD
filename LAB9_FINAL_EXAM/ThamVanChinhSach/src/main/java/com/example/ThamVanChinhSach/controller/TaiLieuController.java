package com.example.ThamVanChinhSach.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ThamVanChinhSach.dto.TaiLieuResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.TaiLieuService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tailieu")
public class TaiLieuController {
    private final TaiLieuService taiLieuService;
    private final JwtTokenProvider jwtTokenProvider;

    public TaiLieuController(TaiLieuService taiLieuService, JwtTokenProvider jwtTokenProvider) {
        this.taiLieuService = taiLieuService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/upload/{maYKien}")
    public ResponseEntity<String> uploadFile(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maYKien,
            @RequestParam("file") MultipartFile file) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String result = taiLieuService.luuFileTaiLieu(maYKien, file);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @GetMapping("/theo-ykien/{maYKien}")
    public ResponseEntity<?> layTheoYKien(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maYKien) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            List<TaiLieuResponse> list = taiLieuService.layTheoYKien(maYKien);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @DeleteMapping("/{maTaiLieu}")
    public ResponseEntity<String> xoaTaiLieu(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maTaiLieu) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được xóa tài liệu!");
            }
            String result = taiLieuService.xoaTaiLieu(maTaiLieu);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
