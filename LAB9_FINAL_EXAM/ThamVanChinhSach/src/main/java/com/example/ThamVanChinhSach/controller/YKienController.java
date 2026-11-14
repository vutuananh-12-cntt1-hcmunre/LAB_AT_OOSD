package com.example.ThamVanChinhSach.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.ThamVanChinhSach.domain.ViPhamNghiepVuException;
import com.example.ThamVanChinhSach.dto.ChiTietYKienResponse;
import com.example.ThamVanChinhSach.dto.DeXuatNhomResponse;
import com.example.ThamVanChinhSach.dto.PhanHoiRequest;
import com.example.ThamVanChinhSach.dto.RaSoatRequest;
import com.example.ThamVanChinhSach.dto.YKienCreateRequest;
import com.example.ThamVanChinhSach.dto.YKienResponse;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.TruyVetService;
import com.example.ThamVanChinhSach.service.YKienService;

@RestController
@RequestMapping("/api/ykien")
@CrossOrigin(origins = "*")
public class YKienController {
    @Autowired
    private YKienService yKienService;
    @Autowired
    private TruyVetService truyVetService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/gui")
    public ResponseEntity<String> guiYKien(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody YKienCreateRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
            }
            int maNguoiDung = jwtTokenProvider.getUserIdFromToken(token);
            String result = yKienService.guiYKien(request, maNguoiDung);
            if (result.startsWith("Gửi ý kiến thất bại")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Có lỗi xảy ra khi gửi ý kiến");
        }
    }

    /**
     * Phân nhóm do cán bộ rà soát (người) xác nhận.
     * Không dùng AI để phân nhóm hay loại ý kiến.
     */
    @PutMapping("/{maYKien}/ra-soat")
    public ResponseEntity<String> raSoatPhanNhom(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maYKien,
            @RequestBody RaSoatRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            // Rà soát bắt buộc có người: RA_SOAT, CAN_BO_CO_QUAN hoặc ADMIN
            if (!"RA_SOAT".equals(role) && !"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Tài khoản của bạn không có quyền rà soát phân nhóm ý kiến!");
            }
            int maNguoiRaSoat = jwtTokenProvider.getUserIdFromToken(token);
            String result = yKienService.raSoatYKien(maYKien, request, maNguoiRaSoat);
            if (result.startsWith("Lỗi rà soát")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Có lỗi xảy ra khi rà soát ý kiến");
        }
    }

    @PutMapping("/{maYKien}/phan-hoi")
    public ResponseEntity<String> phanHoiYKien(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maYKien,
            @RequestBody PhanHoiRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Chỉ Cán bộ cơ quan mới có quyền phản hồi ý kiến!");
            }
            int maNguoiPhanHoi = jwtTokenProvider.getUserIdFromToken(token);
            String result = yKienService.phanHoiYKien(
                    maYKien, request.getKetQuaXuLy(), request.getNoiDungGiaiThich(), maNguoiPhanHoi);
            if (result.startsWith("Lỗi phản hồi ý kiến")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @GetMapping("/danh-sach")
    public ResponseEntity<?> layDanhSachYKien(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            List<YKienResponse> danhSach = yKienService.layDanhSachYKien();
            return ResponseEntity.ok(danhSach);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @GetMapping("/{maYKien}")
    public ResponseEntity<?> layChiTietYKien(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maYKien) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            ChiTietYKienResponse chiTiet = yKienService.layChiTietYKien(maYKien);
            if (chiTiet == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy ý kiến có ID: " + maYKien);
            }
            return ResponseEntity.ok(chiTiet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @GetMapping("/ca-nhan")
    public ResponseEntity<?> layDanhSachYKienCaNhan(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            int maNguoiGui = jwtTokenProvider.getUserIdFromToken(token);
            List<YKienResponse> danhSachCaNhan = yKienService.layDanhSachYKienCaNhan(maNguoiGui);
            return ResponseEntity.ok(danhSachCaNhan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    /**
     * Truy vết chiều Ý kiến -> Dự thảo: các phiên bản dự thảo đã tiếp thu ý kiến này.
     */
    @GetMapping("/{maYKien}/du-thao-lien-ket")
    public ResponseEntity<?> layDuThaoLienKet(
            @PathVariable int maYKien,
            @RequestHeader(value = "Authorization", required = false) String bearerToken) {
        String token = layToken(bearerToken);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token xác thực!");
        }
        try {
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }

        try {
            return ResponseEntity.ok(truyVetService.layDuThaoTiepThuYKien(maYKien));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi truy vấn truy vết: " + e.getMessage());
        }
    }

    /**
     * Gợi ý phân nhóm tự động. Chỉ ghi nhận nhóm ĐỀ XUẤT; cán bộ rà soát
     * vẫn phải gọi /ra-soat để chốt nhóm chính thức. Không loại bỏ ý kiến nào.
     */
    @PostMapping({"/{maYKien}/de-xuat-nhom", "/{maYKien}/ai-de-xuat"})
    public ResponseEntity<?> deXuatNhom(
            @PathVariable int maYKien,
            @RequestHeader(value = "Authorization", required = false) String bearerToken) {
        String token = layToken(bearerToken);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token xác thực!");
        }

        String role;
        try {
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            role = jwtTokenProvider.getRoleFromToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }

        if (!"RA_SOAT".equals(role) && !"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Tài khoản của bạn không có quyền nhận đề xuất phân nhóm!");
        }

        try {
            DeXuatNhomResponse ketQua = yKienService.deXuatNhom(maYKien);
            if (ketQua == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy ý kiến có ID: " + maYKien);
            }
            return ResponseEntity.ok(ketQua);
        } catch (ViPhamNghiepVuException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi đề xuất phân nhóm: " + e.getMessage());
        }
    }

    private String layToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return null;
        }
        String token = bearerToken.substring(7).trim();
        return token.isEmpty() ? null : token;
    }
}
