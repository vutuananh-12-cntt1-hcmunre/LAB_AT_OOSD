package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.TaiLieuResponse;

@Repository
public class TaiLieuRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaiLieuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void luuThongTinTaiLieu(int maYKien, String tenTep, String duongDan, String loaiTep, long kichThuoc) {
        String sql = "INSERT INTO dbo.TAI_LIEU_Y_KIEN (MaYKien, TenTep, DuongDanTep, LoaiTep, KichThuoc, ThoiDiemTaiLen) " +
                     "VALUES (?, ?, ?, ?, ?, SYSDATETIME())";
        jdbcTemplate.update(sql, maYKien, tenTep, duongDan, loaiTep, kichThuoc);
    }

    public List<TaiLieuResponse> layTheoYKien(int maYKien) {
        String sql = "SELECT MaTaiLieu, MaYKien, TenTep, DuongDanTep, LoaiTep, KichThuoc, ThoiDiemTaiLen " +
                     "FROM dbo.TAI_LIEU_Y_KIEN WHERE MaYKien = ? ORDER BY ThoiDiemTaiLen DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TaiLieuResponse tl = new TaiLieuResponse();
            tl.setMaTaiLieu(rs.getInt("MaTaiLieu"));
            tl.setMaYKien(rs.getInt("MaYKien"));
            tl.setTenTep(rs.getString("TenTep"));
            tl.setDuongDanTep(rs.getString("DuongDanTep"));
            tl.setLoaiTep(rs.getString("LoaiTep"));
            long size = rs.getLong("KichThuoc");
            tl.setKichThuoc(rs.wasNull() ? null : size);
            if (rs.getTimestamp("ThoiDiemTaiLen") != null) {
                tl.setThoiDiemTaiLen(rs.getTimestamp("ThoiDiemTaiLen").toLocalDateTime());
            }
            return tl;
        }, maYKien);
    }

    public TaiLieuResponse layChiTiet(int maTaiLieu) {
        String sql = "SELECT MaTaiLieu, MaYKien, TenTep, DuongDanTep, LoaiTep, KichThuoc, ThoiDiemTaiLen " +
                     "FROM dbo.TAI_LIEU_Y_KIEN WHERE MaTaiLieu = ?";
        List<TaiLieuResponse> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            TaiLieuResponse tl = new TaiLieuResponse();
            tl.setMaTaiLieu(rs.getInt("MaTaiLieu"));
            tl.setMaYKien(rs.getInt("MaYKien"));
            tl.setTenTep(rs.getString("TenTep"));
            tl.setDuongDanTep(rs.getString("DuongDanTep"));
            tl.setLoaiTep(rs.getString("LoaiTep"));
            long size = rs.getLong("KichThuoc");
            tl.setKichThuoc(rs.wasNull() ? null : size);
            if (rs.getTimestamp("ThoiDiemTaiLen") != null) {
                tl.setThoiDiemTaiLen(rs.getTimestamp("ThoiDiemTaiLen").toLocalDateTime());
            }
            return tl;
        }, maTaiLieu);
        return list.isEmpty() ? null : list.get(0);
    }

    public void xoaTaiLieu(int maTaiLieu) {
        String sql = "DELETE FROM dbo.TAI_LIEU_Y_KIEN WHERE MaTaiLieu = ?";
        int rows = jdbcTemplate.update(sql, maTaiLieu);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy tài liệu ID: " + maTaiLieu);
        }
    }
}
