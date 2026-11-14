package com.example.ThamVanChinhSach.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThamVanChinhSach.dto.NhomYKienRequest;
import com.example.ThamVanChinhSach.dto.NhomYKienResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.NhomYKienService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/nhom-ykien")
public class NhomYKienController {
    private final NhomYKienService nhomYKienService;
    private final JwtTokenProvider jwtTokenProvider;

    public NhomYKienController(NhomYKienService nhomYKienService, JwtTokenProvider jwtTokenProvider) {
        this.nhomYKienService = nhomYKienService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/theo-chude/{maChuDe}")
    public ResponseEntity<List<NhomYKienResponse>> layDanhSachTheoChuDe(@PathVariable int maChuDe) {
        return ResponseEntity.ok(nhomYKienService.layDanhSachTheoChuDe(maChuDe));
    }

    @PostMapping("/tao-moi")
    public ResponseEntity<String> taoNhom(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody NhomYKienRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được tạo nhóm ý kiến!");
            }
            String result = nhomYKienService.taoNhom(request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @PutMapping("/{maNhom}")
    public ResponseEntity<String> capNhatNhom(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maNhom,
            @RequestBody NhomYKienRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được cập nhật nhóm!");
            }
            String result = nhomYKienService.capNhatNhom(maNhom, request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @DeleteMapping("/{maNhom}")
    public ResponseEntity<String> xoaNhom(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maNhom) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được xóa nhóm!");
            }
            String result = nhomYKienService.xoaNhom(maNhom);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
