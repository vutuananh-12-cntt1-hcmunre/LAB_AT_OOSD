package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.NhomYKienRequest;
import com.example.ThamVanChinhSach.dto.NhomYKienResponse;

@Repository
public class NhomYKienRepository {
    private final JdbcTemplate jdbcTemplate;

    public NhomYKienRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NhomYKienResponse> layDanhSachTheoChuDe(int maChuDe) {
        String sql = "SELECT n.MaNhom, n.MaChuDe, n.TenNhom, n.MoTa, " +
                     "       (SELECT COUNT(*) FROM dbo.Y_KIEN y WHERE y.MaNhom_ChinhThuc = n.MaNhom OR y.MaNhom_AI_DeXuat = n.MaNhom) AS SoYKien " +
                     "FROM dbo.NHOM_Y_KIEN n " +
                     "WHERE n.MaChuDe = ? " +
                     "ORDER BY n.MaNhom";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            NhomYKienResponse nhom = new NhomYKienResponse();
            nhom.setMaNhom(rs.getInt("MaNhom"));
            nhom.setMaChuDe(rs.getInt("MaChuDe"));
            nhom.setTenNhom(rs.getString("TenNhom"));
            nhom.setMoTa(rs.getString("MoTa"));
            nhom.setSoYKien(rs.getInt("SoYKien"));
            return nhom;
        }, maChuDe);
    }

    public Integer taoNhom(NhomYKienRequest request) {
        String sql = "INSERT INTO dbo.NHOM_Y_KIEN (MaChuDe, TenNhom, MoTa) VALUES (?, ?, ?); SELECT SCOPE_IDENTITY();";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                request.getMaChuDe(), request.getTenNhom(), request.getMoTa());
    }

    public void capNhatNhom(int maNhom, String tenNhom, String moTa) {
        String sql = "UPDATE dbo.NHOM_Y_KIEN SET TenNhom = ?, MoTa = ? WHERE MaNhom = ?";
        int rows = jdbcTemplate.update(sql, tenNhom, moTa, maNhom);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy nhóm ý kiến ID: " + maNhom);
        }
    }

    public void xoaNhom(int maNhom) {
        // Kiểm tra còn ý kiến gắn nhóm không
        String checkSql = "SELECT COUNT(*) FROM dbo.Y_KIEN WHERE MaNhom_AI_DeXuat = ? OR MaNhom_ChinhThuc = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, maNhom, maNhom);
        if (count != null && count > 0) {
            throw new RuntimeException("Không thể xóa nhóm vì còn " + count + " ý kiến đang gắn với nhóm này!");
        }
        String sql = "DELETE FROM dbo.NHOM_Y_KIEN WHERE MaNhom = ?";
        int rows = jdbcTemplate.update(sql, maNhom);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy nhóm ý kiến ID: " + maNhom);
        }
    }

    public boolean tonTaiNhom(int maNhom) {
        String sql = "SELECT COUNT(*) FROM dbo.NHOM_Y_KIEN WHERE MaNhom = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, maNhom);
        return count != null && count > 0;
    }
}
