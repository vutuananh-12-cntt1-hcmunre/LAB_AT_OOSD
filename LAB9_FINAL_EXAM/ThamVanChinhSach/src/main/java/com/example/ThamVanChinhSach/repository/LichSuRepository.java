package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.LichSuXuLyResponse;

@Repository
public class LichSuRepository {
    private final JdbcTemplate jdbcTemplate;

    public LichSuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void ghiLichSu(Integer maYKien, Integer maDuThao, Integer maNguoiThucHien, String hanhDong, String ghiChu) {
        String sql = "INSERT INTO dbo.LICH_SU_XU_LY (MaYKien, MaDuThao, MaNguoiThucHien, HanhDong, GhiChu) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, maYKien, maDuThao, maNguoiThucHien, hanhDong, ghiChu);
    }

    public List<LichSuXuLyResponse> layTheoYKien(int maYKien) {
        String sql = "SELECT ls.MaLichSu, ls.MaYKien, ls.MaDuThao, ls.MaNguoiThucHien, " +
                     "       nd.HoTen AS TenNguoiThucHien, ls.HanhDong, ls.ThoiDiem, ls.GhiChu " +
                     "FROM dbo.LICH_SU_XU_LY ls " +
                     "LEFT JOIN dbo.NGUOI_DUNG nd ON ls.MaNguoiThucHien = nd.MaNguoiDung " +
                     "WHERE ls.MaYKien = ? ORDER BY ls.ThoiDiem DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs), maYKien);
    }

    public List<LichSuXuLyResponse> layTheoDuThao(int maDuThao) {
        String sql = "SELECT ls.MaLichSu, ls.MaYKien, ls.MaDuThao, ls.MaNguoiThucHien, " +
                     "       nd.HoTen AS TenNguoiThucHien, ls.HanhDong, ls.ThoiDiem, ls.GhiChu " +
                     "FROM dbo.LICH_SU_XU_LY ls " +
                     "LEFT JOIN dbo.NGUOI_DUNG nd ON ls.MaNguoiThucHien = nd.MaNguoiDung " +
                     "WHERE ls.MaDuThao = ? ORDER BY ls.ThoiDiem DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs), maDuThao);
    }

    private LichSuXuLyResponse map(java.sql.ResultSet rs) throws java.sql.SQLException {
        LichSuXuLyResponse ls = new LichSuXuLyResponse();
        ls.setMaLichSu(rs.getLong("MaLichSu"));
        int maYKien = rs.getInt("MaYKien");
        ls.setMaYKien(rs.wasNull() ? null : maYKien);
        int maDuThao = rs.getInt("MaDuThao");
        ls.setMaDuThao(rs.wasNull() ? null : maDuThao);
        int maNguoi = rs.getInt("MaNguoiThucHien");
        ls.setMaNguoiThucHien(rs.wasNull() ? null : maNguoi);
        ls.setTenNguoiThucHien(rs.getString("TenNguoiThucHien"));
        ls.setHanhDong(rs.getString("HanhDong"));
        if (rs.getTimestamp("ThoiDiem") != null) {
            ls.setThoiDiem(rs.getTimestamp("ThoiDiem").toLocalDateTime());
        }
        ls.setGhiChu(rs.getString("GhiChu"));
        return ls;
    }
}
