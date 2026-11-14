package com.example.ThamVanChinhSach.controller;

import java.util.List;

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

import com.example.ThamVanChinhSach.dto.CapNhatTrangThaiRequest;
import com.example.ThamVanChinhSach.dto.ChuDeRequest;
import com.example.ThamVanChinhSach.dto.ChuDeResponse;
import com.example.ThamVanChinhSach.dto.ChuDeUpdateRequest;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.ChuDeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/chude")
public class ChuDeController {
    private final ChuDeService chuDeService;
    private final JwtTokenProvider jwtTokenProvider;

    public ChuDeController(ChuDeService chuDeService, JwtTokenProvider jwtTokenProvider) {
        this.chuDeService = chuDeService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/danh-sach")
    public ResponseEntity<List<ChuDeResponse>> layDanhSach() {
        return ResponseEntity.ok(chuDeService.layDanhSachChuDe());
    }

    @GetMapping("/{maChuDe}")
    public ResponseEntity<?> layChiTiet(@PathVariable int maChuDe) {
        ChuDeResponse chuDe = chuDeService.layChiTietChuDe(maChuDe);
        if (chuDe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy chủ đề ID: " + maChuDe);
        }
        return ResponseEntity.ok(chuDe);
    }

    @PostMapping("/tao-moi")
    public ResponseEntity<String> taoChuDe(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody ChuDeRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan mới được tạo chủ đề!");
            }
            int maNguoiTao = jwtTokenProvider.getUserIdFromToken(token);
            String result = chuDeService.taoChuDe(request, maNguoiTao);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @PutMapping("/{maChuDe}")
    public ResponseEntity<String> capNhatChuDe(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maChuDe,
            @RequestBody ChuDeUpdateRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được cập nhật chủ đề!");
            }
            String result = chuDeService.capNhatChuDe(maChuDe, request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @PutMapping("/{maChuDe}/trang-thai")
    public ResponseEntity<String> capNhatTrangThai(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maChuDe,
            @RequestBody CapNhatTrangThaiRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được đổi trạng thái chủ đề!");
            }
            String result = chuDeService.capNhatTrangThai(maChuDe, request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
