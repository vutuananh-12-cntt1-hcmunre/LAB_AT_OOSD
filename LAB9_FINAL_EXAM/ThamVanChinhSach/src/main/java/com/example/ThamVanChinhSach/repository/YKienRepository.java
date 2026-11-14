package com.example.ThamVanChinhSach.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.domain.NhomYKien;
import com.example.ThamVanChinhSach.domain.YKien;
import com.example.ThamVanChinhSach.dto.ChiTietYKienResponse;
import com.example.ThamVanChinhSach.dto.YKienResponse;

@Repository
public class YKienRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer guiYKien(int maChuDe, int nguoiGui, String noiDung) {
        String sql = "EXEC dbo.sp_GuiYKienThamVan @MaChuDe = ?, @NguoiGui = ?, @NoiDung = ?";
        return jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            maChuDe,
            nguoiGui,
            noiDung
        );
    }

    public void aiDeXuatNhom(int maYKien, int maNhomAIDeXuat) {
        String sql = "EXEC dbo.sp_AIDeXuatNhomYKien @MaYKien = ?, @MaNhom_AI_DeXuat = ?";
        jdbcTemplate.update(
            sql,
            maYKien,
            maNhomAIDeXuat
        );
    }

    public void raSoatPhanNhom(int maYKien, int maNhomChinhThuc, int nguoiRaSoat) {
        String sql = "EXEC dbo.sp_RaSoatPhanNhom @MaYKien = ?, @MaNhom_ChinhThuc = ?, @NguoiRaSoat = ?";
        jdbcTemplate.update(
            sql,
            maYKien,
            maNhomChinhThuc,
            nguoiRaSoat
        );
    }

    public void phanHoiYKien(int maYKien, String ketQuaXuLy, String noiDungGiaiThich, int nguoiPhanHoi) {
        String sql = "EXEC dbo.sp_PhanHoiYKien @MaYKien = ?, @KetQuaXuLy = ?, @NoiDungGiaiThich = ?, @NguoiPhanHoi = ?";
        jdbcTemplate.update(
            sql,
            maYKien,
            ketQuaXuLy,
            noiDungGiaiThich,
            nguoiPhanHoi
        );
    }

    /** Nạp ý kiến dưới dạng đối tượng miền để kiểm tra nghiệp vụ trước khi ghi. */
    public YKien timTheoMa(int maYKien) {
        String sql = "SELECT MaYKien, MaChuDe, NguoiGui, NoiDung, ThoiDiemGui, TrangThaiXuLy, " +
                     "       MaNhom_AI_DeXuat, MaNhom_ChinhThuc, NguoiRaSoat, ThoiDiemRaSoat " +
                     "FROM dbo.Y_KIEN WHERE MaYKien = ?";
        List<YKien> ketQua = jdbcTemplate.query(sql, (rs, rowNum) -> {
            int nhomAI = rs.getInt("MaNhom_AI_DeXuat");
            Integer maNhomAI = rs.wasNull() ? null : nhomAI;
            int nhomChinhThuc = rs.getInt("MaNhom_ChinhThuc");
            Integer maNhomChinhThuc = rs.wasNull() ? null : nhomChinhThuc;
            int nguoiRaSoat = rs.getInt("NguoiRaSoat");
            Integer maNguoiRaSoat = rs.wasNull() ? null : nguoiRaSoat;
            java.sql.Timestamp thoiDiemRaSoat = rs.getTimestamp("ThoiDiemRaSoat");

            return new YKien(
                    rs.getInt("MaYKien"),
                    rs.getInt("MaChuDe"),
                    rs.getInt("NguoiGui"),
                    rs.getString("NoiDung"),
                    rs.getTimestamp("ThoiDiemGui") == null
                            ? null : rs.getTimestamp("ThoiDiemGui").toLocalDateTime(),
                    rs.getString("TrangThaiXuLy"),
                    maNhomAI,
                    maNhomChinhThuc,
                    maNguoiRaSoat,
                    thoiDiemRaSoat == null ? null : thoiDiemRaSoat.toLocalDateTime());
        }, maYKien);
        return ketQua.isEmpty() ? null : ketQua.get(0);
    }

    public List<NhomYKien> layNhomTheoChuDe(int maChuDe) {
        String sql = "SELECT MaNhom, MaChuDe, TenNhom, MoTa FROM dbo.NHOM_Y_KIEN " +
                     "WHERE MaChuDe = ? ORDER BY MaNhom";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new NhomYKien(
                rs.getInt("MaNhom"),
                rs.getInt("MaChuDe"),
                rs.getString("TenNhom"),
                rs.getString("MoTa")), maChuDe);
    }

    public List<YKienResponse> layDanhSachYKien() {
        String sql = "SELECT MaYKien, MaChuDe, NguoiGui, NoiDung, ThoiDiemGui, TrangThaiXuLy FROM dbo.Y_KIEN ORDER BY ThoiDiemGui DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            YKienResponse ykien = new YKienResponse();
            ykien.setMaYKien(rs.getInt("MaYKien"));
            ykien.setMaChuDe(rs.getInt("MaChuDe"));
            ykien.setNguoiGui(rs.getInt("NguoiGui"));
            ykien.setNoiDung(rs.getString("NoiDung"));
            if (rs.getTimestamp("ThoiDiemGui") != null) {
                ykien.setThoiDiemGui(rs.getTimestamp("ThoiDiemGui").toLocalDateTime());
            }
            ykien.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
            return ykien;
        });
    }

    public ChiTietYKienResponse layChiTietYKien(int maYKien) {
        String sql = "SELECT yk.MaYKien, yk.MaChuDe, yk.NoiDung, yk.ThoiDiemGui, yk.TrangThaiXuLy, " +
                     "yk.MaNhom_AI_DeXuat AS MaNhomAI, yk.MaNhom_ChinhThuc AS MaNhomChinhThuc, " +
                     "yk.NguoiRaSoat, yk.ThoiDiemRaSoat, " +
                     "nhomAI.TenNhom AS TenNhomAI, nhomChinh.TenNhom AS TenNhomChinhThuc, " +
                     "ph.KetQuaXuLy, ph.NoiDungGiaiThich, ph.ThoiDiemPhanHoi " +
                     "FROM dbo.Y_KIEN yk " +
                     "LEFT JOIN dbo.NHOM_Y_KIEN nhomAI ON yk.MaNhom_AI_DeXuat = nhomAI.MaNhom " +
                     "LEFT JOIN dbo.NHOM_Y_KIEN nhomChinh ON yk.MaNhom_ChinhThuc = nhomChinh.MaNhom " +
                     "LEFT JOIN dbo.PHAN_HOI ph ON yk.MaYKien = ph.MaYKien " +
                     "WHERE yk.MaYKien = ?";

        List<ChiTietYKienResponse> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
            ChiTietYKienResponse chiTiet = new ChiTietYKienResponse();
            chiTiet.setMaYKien(rs.getInt("MaYKien"));
            chiTiet.setMaChuDe(rs.getInt("MaChuDe"));
            chiTiet.setNoiDung(rs.getString("NoiDung"));
            if (rs.getTimestamp("ThoiDiemGui") != null) {
                chiTiet.setThoiDiemGui(rs.getTimestamp("ThoiDiemGui").toLocalDateTime());
            }
            chiTiet.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));

            int maNhomAI = rs.getInt("MaNhomAI");
            chiTiet.setMaNhomAI(rs.wasNull() ? null : maNhomAI);
            int maNhomChinhThuc = rs.getInt("MaNhomChinhThuc");
            chiTiet.setMaNhomChinhThuc(rs.wasNull() ? null : maNhomChinhThuc);

            int nguoiRaSoat = rs.getInt("NguoiRaSoat");
            chiTiet.setNguoiRaSoat(rs.wasNull() ? null : nguoiRaSoat);
            if (rs.getTimestamp("ThoiDiemRaSoat") != null) {
                chiTiet.setThoiDiemRaSoat(rs.getTimestamp("ThoiDiemRaSoat").toLocalDateTime());
            }

            chiTiet.setTenNhomAI(rs.getString("TenNhomAI"));
            chiTiet.setTenNhomChinhThuc(rs.getString("TenNhomChinhThuc"));

            chiTiet.setKetQuaXuLy(rs.getString("KetQuaXuLy"));
            chiTiet.setNoiDungGiaiThich(rs.getString("NoiDungGiaiThich"));
            if (rs.getTimestamp("ThoiDiemPhanHoi") != null) {
                chiTiet.setThoiDiemPhanHoi(rs.getTimestamp("ThoiDiemPhanHoi").toLocalDateTime());
            }

            return chiTiet;
        }, maYKien);

        return result.stream().findFirst().orElse(null);
    }

    public List<YKienResponse> layDanhSachYKienCaNhan(int nguoiGui) {
        String sql = "SELECT MaYKien, MaChuDe, NguoiGui, NoiDung, ThoiDiemGui, TrangThaiXuLy " +
                     "FROM dbo.Y_KIEN WHERE NguoiGui = ? ORDER BY ThoiDiemGui DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            YKienResponse ykien = new YKienResponse();
            ykien.setMaYKien(rs.getInt("MaYKien"));
            ykien.setMaChuDe(rs.getInt("MaChuDe"));
            ykien.setNguoiGui(rs.getInt("NguoiGui"));
            ykien.setNoiDung(rs.getString("NoiDung"));
            if (rs.getTimestamp("ThoiDiemGui") != null) {
                ykien.setThoiDiemGui(rs.getTimestamp("ThoiDiemGui").toLocalDateTime());
            }
            ykien.setTrangThaiXuLy(rs.getString("TrangThaiXuLy"));
            return ykien;
        }, nguoiGui);
    }
}
