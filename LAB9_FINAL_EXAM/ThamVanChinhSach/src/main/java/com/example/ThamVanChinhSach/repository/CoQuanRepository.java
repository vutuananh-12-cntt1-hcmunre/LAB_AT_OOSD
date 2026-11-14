package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.CoQuanRequest;
import com.example.ThamVanChinhSach.dto.CoQuanResponse;

@Repository
public class CoQuanRepository {
    private final JdbcTemplate jdbcTemplate;

    public CoQuanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CoQuanResponse> layDanhSach() {
        String sql = "SELECT MaCoQuan, TenCoQuan, EmailLienHe, SoDienThoai, DiaChi, TrangThai FROM dbo.CO_QUAN ORDER BY TenCoQuan";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs));
    }

    public CoQuanResponse layChiTiet(int maCoQuan) {
        String sql = "SELECT MaCoQuan, TenCoQuan, EmailLienHe, SoDienThoai, DiaChi, TrangThai FROM dbo.CO_QUAN WHERE MaCoQuan = ?";
        List<CoQuanResponse> list = jdbcTemplate.query(sql, (rs, rowNum) -> map(rs), maCoQuan);
        return list.isEmpty() ? null : list.get(0);
    }

    public Integer taoCoQuan(CoQuanRequest request) {
        String sql = "INSERT INTO dbo.CO_QUAN (TenCoQuan, EmailLienHe, SoDienThoai, DiaChi) VALUES (?, ?, ?, ?); SELECT SCOPE_IDENTITY();";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                request.getTenCoQuan(), request.getEmailLienHe(), request.getSoDienThoai(), request.getDiaChi());
    }

    public void capNhatCoQuan(int maCoQuan, CoQuanRequest request) {
        String sql = "UPDATE dbo.CO_QUAN SET TenCoQuan = ?, EmailLienHe = ?, SoDienThoai = ?, DiaChi = ? WHERE MaCoQuan = ?";
        int rows = jdbcTemplate.update(sql,
                request.getTenCoQuan(), request.getEmailLienHe(), request.getSoDienThoai(), request.getDiaChi(), maCoQuan);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy cơ quan ID: " + maCoQuan);
        }
    }

    public void capNhatTrangThai(int maCoQuan, boolean trangThai) {
        String sql = "UPDATE dbo.CO_QUAN SET TrangThai = ? WHERE MaCoQuan = ?";
        int rows = jdbcTemplate.update(sql, trangThai, maCoQuan);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy cơ quan ID: " + maCoQuan);
        }
    }

    private CoQuanResponse map(java.sql.ResultSet rs) throws java.sql.SQLException {
        CoQuanResponse cq = new CoQuanResponse();
        cq.setMaCoQuan(rs.getInt("MaCoQuan"));
        cq.setTenCoQuan(rs.getString("TenCoQuan"));
        cq.setEmailLienHe(rs.getString("EmailLienHe"));
        cq.setSoDienThoai(rs.getString("SoDienThoai"));
        cq.setDiaChi(rs.getString("DiaChi"));
        cq.setTrangThai(rs.getBoolean("TrangThai"));
        return cq;
    }
}
