package com.example.ThamVanChinhSach.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThamVanChinhSach.dto.CapNhatNguoiDungRequest;
import com.example.ThamVanChinhSach.dto.NguoiDungResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.NguoiDungService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/nguoidung")
public class NguoiDungController {
    private final NguoiDungService nguoiDungService;
    private final JwtTokenProvider jwtTokenProvider;

    public NguoiDungController(NguoiDungService nguoiDungService, JwtTokenProvider jwtTokenProvider) {
        this.nguoiDungService = nguoiDungService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/danh-sach")
    public ResponseEntity<?> layDanhSach(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"ADMIN".equals(role) && !"CAN_BO_CO_QUAN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có quyền xem danh sách người dùng!");
            }
            List<NguoiDungResponse> list = nguoiDungService.layDanhSach();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @GetMapping("/{maNguoiDung}")
    public ResponseEntity<?> layChiTiet(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maNguoiDung) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            int currentUserId = jwtTokenProvider.getUserIdFromToken(token);
            // Admin/Can bo xem được tất cả; user thường chỉ xem được chính mình
            if (!"ADMIN".equals(role) && !"CAN_BO_CO_QUAN".equals(role) && currentUserId != maNguoiDung) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có quyền xem thông tin người dùng này!");
            }
            NguoiDungResponse nd = nguoiDungService.layChiTiet(maNguoiDung);
            if (nd == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng ID: " + maNguoiDung);
            }
            return ResponseEntity.ok(nd);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @PutMapping("/{maNguoiDung}")
    public ResponseEntity<String> capNhat(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maNguoiDung,
            @RequestBody CapNhatNguoiDungRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Admin mới được cập nhật người dùng!");
            }
            String result = nguoiDungService.capNhatNguoiDung(maNguoiDung, request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
