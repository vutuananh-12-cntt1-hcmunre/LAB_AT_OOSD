package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.domain.LienKetThayDoi;
import com.example.ThamVanChinhSach.dto.DuThaoRequest;
import com.example.ThamVanChinhSach.dto.DuThaoResponse;

@Repository
public class DuThaoRepository {
    private final JdbcTemplate jdbcTemplate;

    public DuThaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer taoDuThao(DuThaoRequest request, int nguoiCapNhat) {
        // Nếu có phiên bản trước → đánh dấu phiên bản cũ là "Đã thay thế"
        if (request.getMaDuThaoTruoc() != null) {
            jdbcTemplate.update(
                    "UPDATE dbo.DU_THAO SET TrangThai = N'Đã thay thế' WHERE MaDuThao = ?",
                    request.getMaDuThaoTruoc());
        }
        String sql = "INSERT INTO dbo.DU_THAO (MaChuDe, TenDuThao, PhienBan, NoiDung, TrangThai, NguoiCapNhat, MaDuThao_Truoc, GhiChuThayDoi) " +
                     "VALUES (?, ?, ?, ?, N'Bản nháp', ?, ?, ?); SELECT SCOPE_IDENTITY();";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                request.getMaChuDe(),
                request.getTenDuThao(),
                request.getPhienBan(),
                request.getNoiDung(),
                nguoiCapNhat,
                request.getMaDuThaoTruoc(),
                request.getGhiChuThayDoi());
    }

    public List<DuThaoResponse> layDanhSachTheoChuDe(int maChuDe) {
        String sql = "SELECT MaDuThao, MaChuDe, TenDuThao, PhienBan, NoiDung, TrangThai, ThoiDiemTao, " +
                     "       NguoiCapNhat, MaDuThao_Truoc, GhiChuThayDoi " +
                     "FROM dbo.DU_THAO WHERE MaChuDe = ? ORDER BY ThoiDiemTao DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> map(rs), maChuDe);
    }

    public DuThaoResponse layChiTiet(int maDuThao) {
        String sql = "SELECT MaDuThao, MaChuDe, TenDuThao, PhienBan, NoiDung, TrangThai, ThoiDiemTao, " +
                     "       NguoiCapNhat, MaDuThao_Truoc, GhiChuThayDoi " +
                     "FROM dbo.DU_THAO WHERE MaDuThao = ?";
        List<DuThaoResponse> list = jdbcTemplate.query(sql, (rs, rowNum) -> map(rs), maDuThao);
        return list.isEmpty() ? null : list.get(0);
    }

    public void congBoDuThao(int maDuThao) {
        String sql = "UPDATE dbo.DU_THAO SET TrangThai = N'Đã công bố' WHERE MaDuThao = ?";
        int rows = jdbcTemplate.update(sql, maDuThao);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy dự thảo ID: " + maDuThao);
        }
    }

    public void taoLienKetThayDoi(LienKetThayDoi lienKet) {
        String sql = "INSERT INTO dbo.LIEN_KET_THAY_DOI (MaDuThao_Moi, MaYKien, MaPhanHoi, NoiDungThayDoi) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                lienKet.getMaDuThaoMoi(),
                lienKet.getMaYKien(),
                lienKet.getMaPhanHoi(),
                lienKet.getNoiDungThayDoi());
    }

    private DuThaoResponse map(java.sql.ResultSet rs) throws java.sql.SQLException {
        DuThaoResponse dt = new DuThaoResponse();
        dt.setMaDuThao(rs.getInt("MaDuThao"));
        dt.setMaChuDe(rs.getInt("MaChuDe"));
        dt.setTenDuThao(rs.getString("TenDuThao"));
        dt.setPhienBan(rs.getString("PhienBan"));
        dt.setNoiDung(rs.getString("NoiDung"));
        dt.setTrangThai(rs.getString("TrangThai"));
        if (rs.getTimestamp("ThoiDiemTao") != null) {
            dt.setThoiDiemTao(rs.getTimestamp("ThoiDiemTao").toLocalDateTime());
        }
        dt.setNguoiCapNhat(rs.getInt("NguoiCapNhat"));
        int maTruoc = rs.getInt("MaDuThao_Truoc");
        dt.setMaDuThaoTruoc(rs.wasNull() ? null : maTruoc);
        dt.setGhiChuThayDoi(rs.getString("GhiChuThayDoi"));
        return dt;
    }
}
