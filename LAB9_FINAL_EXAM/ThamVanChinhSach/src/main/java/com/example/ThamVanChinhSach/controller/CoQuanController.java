package com.example.ThamVanChinhSach.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThamVanChinhSach.dto.CoQuanRequest;
import com.example.ThamVanChinhSach.dto.CoQuanResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.CoQuanService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/coquan")
public class CoQuanController {
    private final CoQuanService coQuanService;
    private final JwtTokenProvider jwtTokenProvider;

    public CoQuanController(CoQuanService coQuanService, JwtTokenProvider jwtTokenProvider) {
        this.coQuanService = coQuanService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/danh-sach")
    public ResponseEntity<List<CoQuanResponse>> layDanhSach() {
        return ResponseEntity.ok(coQuanService.layDanhSach());
    }

    @GetMapping("/{maCoQuan}")
    public ResponseEntity<?> layChiTiet(@PathVariable int maCoQuan) {
        CoQuanResponse cq = coQuanService.layChiTiet(maCoQuan);
        if (cq == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy cơ quan ID: " + maCoQuan);
        }
        return ResponseEntity.ok(cq);
    }

    @PostMapping("/tao-moi")
    public ResponseEntity<String> taoCoQuan(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody CoQuanRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Admin mới được tạo cơ quan!");
            }
            String result = coQuanService.taoCoQuan(request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @PutMapping("/{maCoQuan}")
    public ResponseEntity<String> capNhat(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maCoQuan,
            @RequestBody CoQuanRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Admin mới được cập nhật cơ quan!");
            }
            String result = coQuanService.capNhatCoQuan(maCoQuan, request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @PutMapping("/{maCoQuan}/trang-thai")
    public ResponseEntity<String> capNhatTrangThai(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maCoQuan,
            @RequestBody Map<String, Boolean> body) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Admin mới được đổi trạng thái cơ quan!");
            }
            Boolean trangThai = body.get("trangThai");
            if (trangThai == null) {
                return ResponseEntity.badRequest().body("Lỗi: thiếu trường trangThai!");
            }
            String result = coQuanService.capNhatTrangThai(maCoQuan, trangThai);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
