package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.ChuDeRequest;
import com.example.ThamVanChinhSach.dto.ChuDeResponse;
import com.example.ThamVanChinhSach.dto.ChuDeUpdateRequest;

@Repository
public class ChuDeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ChuDeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void taoChuDe(ChuDeRequest request, int nguoiTao) {
        String sql = "INSERT INTO dbo.CHU_DE_THAM_VAN (MaCoQuan, TieuDe, NoiDungMoTa, ThoiGianBatDau, ThoiGianKetThuc, NguoiTao, TrangThai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, N'Đang mở')";
        jdbcTemplate.update(sql, request.getMaCoQuan(), request.getTieuDe(), request.getNoiDungMoTa(),
                request.getThoiGianBatDau(), request.getThoiGianKetThuc(), nguoiTao);
    }

    public List<ChuDeResponse> layDanhSachChuDe() {
        String sql = "SELECT MaChuDe, TieuDe, NoiDungMoTa, ThoiGianBatDau, ThoiGianKetThuc, TrangThai " +
                     "FROM dbo.CHU_DE_THAM_VAN ORDER BY NgayTao DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapChuDe(rs));
    }

    public ChuDeResponse layChiTietChuDe(int maChuDe) {
        String sql = "SELECT MaChuDe, TieuDe, NoiDungMoTa, ThoiGianBatDau, ThoiGianKetThuc, TrangThai " +
                     "FROM dbo.CHU_DE_THAM_VAN WHERE MaChuDe = ?";
        List<ChuDeResponse> list = jdbcTemplate.query(sql, (rs, rowNum) -> mapChuDe(rs), maChuDe);
        return list.isEmpty() ? null : list.get(0);
    }

    public void capNhatChuDe(int maChuDe, ChuDeUpdateRequest request) {
        String sql = "UPDATE dbo.CHU_DE_THAM_VAN SET TieuDe = ?, NoiDungMoTa = ?, ThoiGianBatDau = ?, ThoiGianKetThuc = ? " +
                     "WHERE MaChuDe = ?";
        int rows = jdbcTemplate.update(sql,
                request.getTieuDe(),
                request.getNoiDungMoTa(),
                request.getThoiGianBatDau(),
                request.getThoiGianKetThuc(),
                maChuDe);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy chủ đề ID: " + maChuDe);
        }
    }

    public void capNhatTrangThai(int maChuDe, String trangThai) {
        String sql = "UPDATE dbo.CHU_DE_THAM_VAN SET TrangThai = ? WHERE MaChuDe = ?";
        int rows = jdbcTemplate.update(sql, trangThai, maChuDe);
        if (rows == 0) {
            throw new RuntimeException("Không tìm thấy chủ đề ID: " + maChuDe);
        }
    }

    private ChuDeResponse mapChuDe(java.sql.ResultSet rs) throws java.sql.SQLException {
        ChuDeResponse chuDe = new ChuDeResponse();
        chuDe.setMaChuDe(rs.getInt("MaChuDe"));
        chuDe.setTieuDe(rs.getString("TieuDe"));
        chuDe.setNoiDungMoTa(rs.getString("NoiDungMoTa"));
        if (rs.getTimestamp("ThoiGianBatDau") != null) {
            chuDe.setThoiGianBatDau(rs.getTimestamp("ThoiGianBatDau").toLocalDateTime());
        }
        if (rs.getTimestamp("ThoiGianKetThuc") != null) {
            chuDe.setThoiGianKetThuc(rs.getTimestamp("ThoiGianKetThuc").toLocalDateTime());
        }
        chuDe.setTrangThai(rs.getString("TrangThai"));
        return chuDe;
    }
}
