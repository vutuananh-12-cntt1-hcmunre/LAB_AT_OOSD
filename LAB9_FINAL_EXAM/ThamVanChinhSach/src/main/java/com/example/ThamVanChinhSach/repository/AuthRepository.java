package com.example.ThamVanChinhSach.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.RegisterRequest;

@Repository
public class AuthRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer dangKyNguoiDung(RegisterRequest request, String matKhauMaHoa) {
        try {
            String sql = "EXEC dbo.sp_DangKyNguoiDung @HoTen = ?, @Email = ?, @MatKhau = ?, @SoDienThoai = ?, @DiaChi = ?, @MaVaiTro = ?, @MaCoQuan = ?";
            return jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    request.getHoTen(),
                    request.getEmail(),
                    matKhauMaHoa,
                    request.getSoDienThoai(),
                    request.getDiaChi(),
                    request.getMaVaiTro(),
                    request.getMaCoQuan()
            );
        } catch (DataAccessException e) {
            throw new RuntimeException(lamSachLoiDangKy(e.getMessage()), e);
        }
    }

    public LoginResponse dangNhap(String email, String matKhau) {
        try {
            String sql = "EXEC dbo.sp_DangNhap @Email = ?, @MatKhau = ?";
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> {
                        LoginResponse response = new LoginResponse();
                        response.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                        response.setHoTen(rs.getString("HoTen"));
                        response.setEmail(rs.getString("Email"));
                        response.setSoDienThoai(rs.getString("SoDienThoai"));
                        response.setDiaChi(rs.getString("DiaChi"));
                        response.setMaVaiTro(rs.getString("MaVaiTro"));
                        response.setTenVaiTro(rs.getString("TenVaiTro"));
                        int maCoQuan = rs.getInt("MaCoQuan");
                        response.setMaCoQuan(rs.wasNull() ? null : maCoQuan);
                        response.setTenCoQuan(rs.getString("TenCoQuan"));
                        return response;
                    },
                    email,
                    matKhau
            );
        } catch (DataAccessException e) {
            // Không để chuỗi PreparedStatement / SQLException ra ngoài
            throw new RuntimeException(lamSachLoiDangNhap(e.getMessage()));
        }
    }

    private static String lamSachLoiDangNhap(String msg) {
        if (msg == null) {
            return "Email hoặc mật khẩu không đúng!";
        }
        String lower = msg.toLowerCase();
        if (msg.contains("Email không tồn tại") || lower.contains("email không tồn tại")) {
            return "Email không tồn tại trong hệ thống!";
        }
        if (msg.contains("Mật khẩu không đúng") || lower.contains("mật khẩu không đúng")) {
            return "Mật khẩu không đúng!";
        }
        if (msg.contains("bị khóa") || lower.contains("đã bị khóa") || lower.contains("khóa")) {
            return "Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên.";
        }
        if (lower.contains("empty result") || lower.contains("incorrect result size")) {
            return "Email hoặc mật khẩu không đúng!";
        }
        return "Email hoặc mật khẩu không đúng!";
    }

    private static String lamSachLoiDangKy(String msg) {
        if (msg == null) {
            return "Đăng ký thất bại. Vui lòng thử lại.";
        }
        if (msg.contains("Email") && (msg.toLowerCase().contains("unique") || msg.toLowerCase().contains("đã tồn tại"))) {
            return "Email này đã được đăng ký!";
        }
        if (msg.toLowerCase().contains("unique") || msg.contains("UQ_NGUOI_DUNG")) {
            return "Email này đã được đăng ký!";
        }
        return "Đăng ký thất bại. Vui lòng kiểm tra thông tin và thử lại.";
    }
}
