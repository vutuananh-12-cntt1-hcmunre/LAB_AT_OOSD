package com.example.ThamVanChinhSach.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThamVanChinhSach.dto.CongBoKetQuaRequest;
import com.example.ThamVanChinhSach.dto.CongBoKetQuaResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.CongBoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/cong-bo")
public class CongBoController {
    private final CongBoService congBoService;
    private final JwtTokenProvider jwtTokenProvider;

    public CongBoController(CongBoService congBoService, JwtTokenProvider jwtTokenProvider) {
        this.congBoService = congBoService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/danh-sach")
    public ResponseEntity<List<CongBoKetQuaResponse>> layTatCa() {
        return ResponseEntity.ok(congBoService.layTatCa());
    }

    @GetMapping("/theo-chude/{maChuDe}")
    public ResponseEntity<List<CongBoKetQuaResponse>> layTheoChuDe(@PathVariable int maChuDe) {
        return ResponseEntity.ok(congBoService.layDanhSachTheoChuDe(maChuDe));
    }

    @PostMapping("/tao-moi")
    public ResponseEntity<String> taoCongBo(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody CongBoKetQuaRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được công bố kết quả!");
            }
            int maNguoi = jwtTokenProvider.getUserIdFromToken(token);
            String result = congBoService.taoCongBo(request, maNguoi);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
