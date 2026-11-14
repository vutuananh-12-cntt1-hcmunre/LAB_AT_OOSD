package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.CapNhatNguoiDungRequest;
import com.example.ThamVanChinhSach.dto.NguoiDungResponse;

@Repository
public class NguoiDungRepository {
    private final JdbcTemplate jdbcTemplate;

    public NguoiDungRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NguoiDungResponse> layDanhSach() {
        String sql = "SELECT u.MaNguoiDung, u.HoTen, u.Email, u.SoDienThoai, u.DiaChi, " +
                     "       u.MaVaiTro, v.TenVaiTro, u.MaCoQuan, c.TenCoQuan, u.TrangThai, u.NgayTao " +
                     "FROM dbo.NGUOI_DUNG u " +
                     "INNER JOIN dbo.VAI_TRO v ON u.MaVaiTro = v.MaVaiTro " +
                     "LEFT JOIN dbo.CO_QUAN c ON u.MaCoQuan = c.MaCoQuan " +
                     "ORDER BY u.NgayTao DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs));
    }

    public NguoiDungResponse layChiTiet(int maNguoiDung) {
        String sql = "SELECT u.MaNguoiDung, u.HoTen, u.Email, u.SoDienThoai, u.DiaChi, " +
                     "       u.MaVaiTro, v.TenVaiTro, u.MaCoQuan, c.TenCoQuan, u.TrangThai, u.NgayTao " +
                     "FROM dbo.NGUOI_DUNG u " +
                     "INNER JOIN dbo.VAI_TRO v ON u.MaVaiTro = v.MaVaiTro " +
                     "LEFT JOIN dbo.CO_QUAN c ON u.MaCoQuan = c.MaCoQuan " +
                     "WHERE u.MaNguoiDung = ?";
        List<NguoiDungResponse> list = jdbcTemplate.query(sql, (rs, rowNum) -> map(rs), maNguoiDung);
        return list.isEmpty() ? null : list.get(0);
    }

    public void capNhatNguoiDung(int maNguoiDung, CapNhatNguoiDungRequest request) {
        StringBuilder sql = new StringBuilder("UPDATE dbo.NGUOI_DUNG SET ");
        java.util.List<Object> params = new java.util.ArrayList<>();
        boolean first = true;

        if (request.getMaVaiTro() != null) {
            sql.append("MaVaiTro = ?");
            params.add(request.getMaVaiTro());
            first = false;
        }
        if (request.getMaCoQuan() != null) {
            if (!first) sql.append(", ");
            sql.append("MaCoQuan = ?");
            params.add(request.getMaCoQuan());
            first = false;
        }
        if (request.getTrangThai() != null) {
            if (!first) sql.append(", ");
            sql.append("TrangThai = ?");
            params.add(request.getTrangThai());
            first = false;
        }

        if (first) {
            throw new RuntimeException("Không có trường nào để cập nhật!");
        }

        sql.append(" WHERE MaNguoiDung = ?");
        params.add(maNguoiDung);

        int rows = jdbcTemplate.update(sql.toString(), params.toArray());
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy người dùng ID: " + maNguoiDung);
        }
    }

    private NguoiDungResponse map(java.sql.ResultSet rs) throws java.sql.SQLException {
        NguoiDungResponse nd = new NguoiDungResponse();
        nd.setMaNguoiDung(rs.getInt("MaNguoiDung"));
        nd.setHoTen(rs.getString("HoTen"));
        nd.setEmail(rs.getString("Email"));
        nd.setSoDienThoai(rs.getString("SoDienThoai"));
        nd.setDiaChi(rs.getString("DiaChi"));
        nd.setMaVaiTro(rs.getString("MaVaiTro"));
        nd.setTenVaiTro(rs.getString("TenVaiTro"));
        int maCoQuan = rs.getInt("MaCoQuan");
        nd.setMaCoQuan(rs.wasNull() ? null : maCoQuan);
        nd.setTenCoQuan(rs.getString("TenCoQuan"));
        nd.setTrangThai(rs.getBoolean("TrangThai"));
        if (rs.getTimestamp("NgayTao") != null) {
            nd.setNgayTao(rs.getTimestamp("NgayTao").toLocalDateTime());
        }
        return nd;
    }
}
