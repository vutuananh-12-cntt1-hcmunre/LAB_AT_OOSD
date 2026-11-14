package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.DuThaoTiepThuResponse;
import com.example.ThamVanChinhSach.dto.LienKetTruyVetResponse;

/**
 * Truy vấn truy vết hai chiều giữa ý kiến/phản hồi và phiên bản dự thảo.
 * Tách riêng khỏi repository nghiệp vụ để giữ mỗi lớp một trách nhiệm (SRP).
 */
@Repository
public class TruyVetRepository {
    private final JdbcTemplate jdbcTemplate;

    public TruyVetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Dự thảo -> danh sách ý kiến làm căn cứ cho phiên bản này. */
    public List<LienKetTruyVetResponse> layYKienCuaDuThao(int maDuThao) {
        String sql = "SELECT lkt.MaLienKet, lkt.MaYKien, lkt.MaPhanHoi, lkt.NoiDungThayDoi, " +
                     "       yk.NoiDung AS NoiDungYKien, yk.NguoiGui, yk.TrangThaiXuLy, " +
                     "       yk.NguoiRaSoat, yk.ThoiDiemRaSoat, " +
                     "       nd.HoTen AS TenNguoiGui, rs.HoTen AS TenNguoiRaSoat, " +
                     "       nhom.TenNhom AS TenNhomChinhThuc, " +
                     "       ph.KetQuaXuLy, ph.NoiDungGiaiThich, ph.ThoiDiemPhanHoi " +
                     "FROM dbo.LIEN_KET_THAY_DOI lkt " +
                     "INNER JOIN dbo.Y_KIEN yk ON yk.MaYKien = lkt.MaYKien " +
                     "LEFT JOIN dbo.NGUOI_DUNG nd ON nd.MaNguoiDung = yk.NguoiGui " +
                     "LEFT JOIN dbo.NGUOI_DUNG rs ON rs.MaNguoiDung = yk.NguoiRaSoat " +
                     "LEFT JOIN dbo.NHOM_Y_KIEN nhom ON nhom.MaNhom = yk.MaNhom_ChinhThuc " +
                     "LEFT JOIN dbo.PHAN_HOI ph ON ph.MaPhanHoi = lkt.MaPhanHoi " +
                     "WHERE lkt.MaDuThao_Moi = ? ORDER BY lkt.MaLienKet";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LienKetTruyVetResponse dto = new LienKetTruyVetResponse();
            dto.setMaLienKet(rs.getInt("MaLienKet"));
            dto.setMaYKien(rs.getInt("MaYKien"));
            dto.setNoiDungYKien(rs.getString("NoiDungYKien"));
            dto.setNguoiGui(rs.getInt("NguoiGui"));
            dto.setTenNguoiGui(rs.getString("TenNguoiGui"));
            dto.setTrangThaiYKien(rs.getString("TrangThaiXuLy"));

            int nguoiRaSoat = rs.getInt("NguoiRaSoat");
            dto.setNguoiRaSoat(rs.wasNull() ? null : nguoiRaSoat);
            dto.setTenNguoiRaSoat(rs.getString("TenNguoiRaSoat"));
            if (rs.getTimestamp("ThoiDiemRaSoat") != null) {
                dto.setThoiDiemRaSoat(rs.getTimestamp("ThoiDiemRaSoat").toLocalDateTime());
            }
            dto.setTenNhomChinhThuc(rs.getString("TenNhomChinhThuc"));

            int maPhanHoi = rs.getInt("MaPhanHoi");
            dto.setMaPhanHoi(rs.wasNull() ? null : maPhanHoi);
            dto.setKetQuaXuLy(rs.getString("KetQuaXuLy"));
            dto.setNoiDungGiaiThich(rs.getString("NoiDungGiaiThich"));
            if (rs.getTimestamp("ThoiDiemPhanHoi") != null) {
                dto.setThoiDiemPhanHoi(rs.getTimestamp("ThoiDiemPhanHoi").toLocalDateTime());
            }
            dto.setNoiDungThayDoi(rs.getString("NoiDungThayDoi"));
            return dto;
        }, maDuThao);
    }

    /** Ý kiến -> danh sách phiên bản dự thảo đã tiếp thu ý kiến này. */
    public List<DuThaoTiepThuResponse> layDuThaoTiepThuYKien(int maYKien) {
        String sql = "SELECT lkt.MaLienKet, lkt.NoiDungThayDoi, " +
                     "       dt.MaDuThao, dt.TenDuThao, dt.PhienBan, dt.TrangThai, dt.ThoiDiemTao " +
                     "FROM dbo.LIEN_KET_THAY_DOI lkt " +
                     "INNER JOIN dbo.DU_THAO dt ON dt.MaDuThao = lkt.MaDuThao_Moi " +
                     "WHERE lkt.MaYKien = ? ORDER BY dt.ThoiDiemTao DESC, dt.MaDuThao DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DuThaoTiepThuResponse dto = new DuThaoTiepThuResponse();
            dto.setMaLienKet(rs.getInt("MaLienKet"));
            dto.setMaDuThao(rs.getInt("MaDuThao"));
            dto.setTenDuThao(rs.getString("TenDuThao"));
            dto.setPhienBan(rs.getString("PhienBan"));
            dto.setTrangThaiDuThao(rs.getString("TrangThai"));
            if (rs.getTimestamp("ThoiDiemTao") != null) {
                dto.setThoiDiemTao(rs.getTimestamp("ThoiDiemTao").toLocalDateTime());
            }
            dto.setNoiDungThayDoi(rs.getString("NoiDungThayDoi"));
            return dto;
        }, maYKien);
    }

    /**
     * Tìm phản hồi gần nhất của ý kiến (ưu tiên đúng kết quả xử lý nếu có)
     * để gắn vào liên kết thay đổi. Trả null nếu ý kiến chưa được phản hồi.
     */
    public Integer timMaPhanHoiGanNhat(int maYKien, String ketQuaXuLy) {
        if (ketQuaXuLy != null && !ketQuaXuLy.trim().isEmpty()) {
            String sql = "SELECT TOP 1 MaPhanHoi FROM dbo.PHAN_HOI WHERE MaYKien = ? AND KetQuaXuLy = ? " +
                         "ORDER BY ThoiDiemPhanHoi DESC, MaPhanHoi DESC";
            List<Integer> ds = jdbcTemplate.queryForList(sql, Integer.class, maYKien, ketQuaXuLy);
            if (!ds.isEmpty()) {
                return ds.get(0);
            }
        }
        String sql = "SELECT TOP 1 MaPhanHoi FROM dbo.PHAN_HOI WHERE MaYKien = ? " +
                     "ORDER BY ThoiDiemPhanHoi DESC, MaPhanHoi DESC";
        List<Integer> ds = jdbcTemplate.queryForList(sql, Integer.class, maYKien);
        return ds.isEmpty() ? null : ds.get(0);
    }
}
