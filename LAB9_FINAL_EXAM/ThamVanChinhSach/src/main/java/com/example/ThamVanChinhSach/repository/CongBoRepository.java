package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.CongBoKetQuaRequest;
import com.example.ThamVanChinhSach.dto.CongBoKetQuaResponse;

@Repository
public class CongBoRepository {
    private final JdbcTemplate jdbcTemplate;

    public CongBoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer taoCongBo(CongBoKetQuaRequest request, int nguoiCongBo) {
        String sql = "INSERT INTO dbo.CONG_BO_KET_QUA (MaChuDe, MaDuThao, NguoiCongBo, NoiDungCongBo) " +
                     "VALUES (?, ?, ?, ?); SELECT SCOPE_IDENTITY();";
        Integer maCongBo = jdbcTemplate.queryForObject(sql, Integer.class,
                request.getMaChuDe(), request.getMaDuThao(), nguoiCongBo, request.getNoiDungCongBo());

        // Cập nhật trạng thái chủ đề → Đã công bố
        jdbcTemplate.update("UPDATE dbo.CHU_DE_THAM_VAN SET TrangThai = N'Đã công bố' WHERE MaChuDe = ?",
                request.getMaChuDe());

        // Cập nhật dự thảo → Đã công bố (nếu chưa)
        jdbcTemplate.update("UPDATE dbo.DU_THAO SET TrangThai = N'Đã công bố' WHERE MaDuThao = ?",
                request.getMaDuThao());

        return maCongBo;
    }

    public List<CongBoKetQuaResponse> layDanhSachTheoChuDe(int maChuDe) {
        String sql = "SELECT cb.MaCongBo, cb.MaChuDe, cb.MaDuThao, cb.NguoiCongBo, cb.ThoiDiemCongBo, cb.NoiDungCongBo, " +
                     "       cd.TieuDe AS TenChuDe, dt.TenDuThao, dt.PhienBan " +
                     "FROM dbo.CONG_BO_KET_QUA cb " +
                     "INNER JOIN dbo.CHU_DE_THAM_VAN cd ON cb.MaChuDe = cd.MaChuDe " +
                     "INNER JOIN dbo.DU_THAO dt ON cb.MaDuThao = dt.MaDuThao " +
                     "WHERE cb.MaChuDe = ? ORDER BY cb.ThoiDiemCongBo DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs), maChuDe);
    }

    public List<CongBoKetQuaResponse> layTatCa() {
        String sql = "SELECT cb.MaCongBo, cb.MaChuDe, cb.MaDuThao, cb.NguoiCongBo, cb.ThoiDiemCongBo, cb.NoiDungCongBo, " +
                     "       cd.TieuDe AS TenChuDe, dt.TenDuThao, dt.PhienBan " +
                     "FROM dbo.CONG_BO_KET_QUA cb " +
                     "INNER JOIN dbo.CHU_DE_THAM_VAN cd ON cb.MaChuDe = cd.MaChuDe " +
                     "INNER JOIN dbo.DU_THAO dt ON cb.MaDuThao = dt.MaDuThao " +
                     "ORDER BY cb.ThoiDiemCongBo DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs));
    }

    private CongBoKetQuaResponse map(java.sql.ResultSet rs) throws java.sql.SQLException {
        CongBoKetQuaResponse cb = new CongBoKetQuaResponse();
        cb.setMaCongBo(rs.getInt("MaCongBo"));
        cb.setMaChuDe(rs.getInt("MaChuDe"));
        cb.setMaDuThao(rs.getInt("MaDuThao"));
        cb.setNguoiCongBo(rs.getInt("NguoiCongBo"));
        if (rs.getTimestamp("ThoiDiemCongBo") != null) {
            cb.setThoiDiemCongBo(rs.getTimestamp("ThoiDiemCongBo").toLocalDateTime());
        }
        cb.setNoiDungCongBo(rs.getString("NoiDungCongBo"));
        cb.setTenChuDe(rs.getString("TenChuDe"));
        cb.setTenDuThao(rs.getString("TenDuThao"));
        cb.setPhienBan(rs.getString("PhienBan"));
        return cb;
    }
}
