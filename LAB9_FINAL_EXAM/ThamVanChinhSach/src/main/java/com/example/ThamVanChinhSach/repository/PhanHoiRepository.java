package com.example.ThamVanChinhSach.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhanHoiRepository {
    private final JdbcTemplate jdbcTemplate;

    public PhanHoiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void phanHoiTheoNhom(int maNhom, String ketQuaXuLy, String noiDungGiaiThich, int nguoiPhanHoi) {
        String sql = "INSERT INTO dbo.PHAN_HOI (MaNhom, KetQuaXuLy, NoiDungGiaiThich, NguoiPhanHoi, ThoiDiemPhanHoi) " +
                     "VALUES (?, ?, ?, ?, SYSDATETIME())";
        jdbcTemplate.update(sql, maNhom, ketQuaXuLy, noiDungGiaiThich, nguoiPhanHoi);

        // Cập nhật trạng thái tất cả ý kiến thuộc nhóm chính thức này thành "Đã phản hồi"
        String updateSql = "UPDATE dbo.Y_KIEN SET TrangThaiXuLy = N'Đã phản hồi' " +
                           "WHERE MaNhom_ChinhThuc = ? AND TrangThaiXuLy <> N'Đã phản hồi'";
        jdbcTemplate.update(updateSql, maNhom);
    }
}
