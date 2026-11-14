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

import com.example.ThamVanChinhSach.dto.DuThaoRequest;
import com.example.ThamVanChinhSach.dto.DuThaoResponse;
import com.example.ThamVanChinhSach.dto.LienKetThayDoiRequest;
import com.example.ThamVanChinhSach.dto.TaoLienKetTuDuThaoRequest;
import com.example.ThamVanChinhSach.security.JwtTokenProvider;
import com.example.ThamVanChinhSach.service.DuThaoService;
import com.example.ThamVanChinhSach.service.TruyVetService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/duthao")
public class DuThaoController {
    private final DuThaoService duThaoService;
    private final TruyVetService truyVetService;
    private final JwtTokenProvider jwtTokenProvider;

    public DuThaoController(DuThaoService duThaoService,
                            TruyVetService truyVetService,
                            JwtTokenProvider jwtTokenProvider) {
        this.duThaoService = duThaoService;
        this.truyVetService = truyVetService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/theo-chude/{maChuDe}")
    public ResponseEntity<List<DuThaoResponse>> layDanhSachTheoChuDe(@PathVariable int maChuDe) {
        return ResponseEntity.ok(duThaoService.layDanhSachTheoChuDe(maChuDe));
    }

    /**
     * Truy vết chiều Ý kiến -> Dự thảo: các phiên bản dự thảo đã tiếp thu ý kiến.
     * (Giao diện chi tiết ý kiến gọi endpoint này.)
     */
    @GetMapping("/theo-ykien/{maYKien}")
    public ResponseEntity<?> layDuThaoTheoYKien(@PathVariable int maYKien,
                                                @RequestHeader(value = "Authorization", required = false) String bearerToken) {
        String token;
        try {
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token xác thực!");
            }
            token = bearerToken.substring(7).trim();
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

    @GetMapping("/{maDuThao}")
    public ResponseEntity<?> layChiTiet(@PathVariable int maDuThao) {
        DuThaoResponse dt = duThaoService.layChiTiet(maDuThao);
        if (dt == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy dự thảo ID: " + maDuThao);
        }
        return ResponseEntity.ok(dt);
    }

    /**
     * Truy vết chiều Dự thảo -> Ý kiến: các ý kiến/phản hồi làm căn cứ cho phiên bản này.
     */
    @GetMapping("/{maDuThao}/y-kien-lien-ket")
    public ResponseEntity<?> layYKienLienKet(@PathVariable int maDuThao,
                                             @RequestHeader(value = "Authorization", required = false) String bearerToken) {
        String token;
        try {
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token xác thực!");
            }
            token = bearerToken.substring(7).trim();
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }

        try {
            if (duThaoService.layChiTiet(maDuThao) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy dự thảo ID: " + maDuThao);
            }
            return ResponseEntity.ok(truyVetService.layYKienCuaDuThao(maDuThao));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi truy vấn truy vết: " + e.getMessage());
        }
    }

    @PostMapping("/tao-moi")
    public ResponseEntity<String> taoDuThao(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody DuThaoRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được tạo dự thảo!");
            }
            int maNguoi = jwtTokenProvider.getUserIdFromToken(token);
            String result = duThaoService.taoDuThao(request, maNguoi);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    @PutMapping("/{maDuThao}/cong-bo")
    public ResponseEntity<String> congBo(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maDuThao) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được công bố dự thảo!");
            }
            String result = duThaoService.congBoDuThao(maDuThao);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }

    /**
     * Tạo liên kết thay đổi từ giao diện chi tiết dự thảo.
     * Mã dự thảo lấy từ đường dẫn; phản hồi được chọn tự động theo loại tiếp thu.
     */
    @PostMapping("/{maDuThao}/lien-ket-thay-doi")
    public ResponseEntity<String> taoLienKetTheoDuThao(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable int maDuThao,
            @RequestBody TaoLienKetTuDuThaoRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được liên kết thay đổi!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }

        try {
            if (duThaoService.layChiTiet(maDuThao) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Lỗi liên kết thay đổi: Không tìm thấy dự thảo ID: " + maDuThao);
            }
            Integer maPhanHoi = truyVetService.timMaPhanHoiGanNhat(
                    request.getMaYKien(), request.getLoaiThayDoi());
            LienKetThayDoiRequest lienKet = new LienKetThayDoiRequest();
            lienKet.setMaDuThaoMoi(maDuThao);
            lienKet.setMaYKien(request.getMaYKien());
            lienKet.setMaPhanHoi(maPhanHoi);
            lienKet.setNoiDungThayDoi(request.getMoTaThayDoi());

            String result = duThaoService.taoLienKetThayDoi(lienKet);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi tạo liên kết: " + e.getMessage());
        }
    }

    @PostMapping("/lien-ket-thay-doi")
    public ResponseEntity<String> taoLienKet(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody LienKetThayDoiRequest request) {
        try {
            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
            }
            String role = jwtTokenProvider.getRoleFromToken(token);
            if (!"CAN_BO_CO_QUAN".equals(role) && !"ADMIN".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Chỉ Cán bộ cơ quan hoặc Admin mới được liên kết thay đổi!");
            }
            String result = duThaoService.taoLienKetThayDoi(request);
            if (result.startsWith("Lỗi")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Lỗi xác thực Token!");
        }
    }
}
