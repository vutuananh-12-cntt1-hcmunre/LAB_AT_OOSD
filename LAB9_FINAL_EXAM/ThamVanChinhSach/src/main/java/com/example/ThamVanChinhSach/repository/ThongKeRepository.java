package com.example.ThamVanChinhSach.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ThamVanChinhSach.dto.ThongKeYKienResponse;

@Repository
public class ThongKeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThongKeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ThongKeYKienResponse layThongKeChung() {
        String sql = "SELECT " +
                     "COUNT(*) AS TongSo, " +
                     "SUM(CASE WHEN TrangThaiXuLy = N'Mới tiếp nhận' THEN 1 ELSE 0 END) AS SoMoi, " +
                     "SUM(CASE WHEN TrangThaiXuLy = N'Đã phản hồi' THEN 1 ELSE 0 END) AS SoDaPhanHoi " +
                     "FROM dbo.Y_KIEN";
                     
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            ThongKeYKienResponse thongKe = new ThongKeYKienResponse();
            thongKe.setTongSoYKien(rs.getInt("TongSo"));
            thongKe.setSoYKienMoi(rs.getInt("SoMoi"));
            thongKe.setSoYKienDaPhanHoi(rs.getInt("SoDaPhanHoi"));
            
            // Những ý kiến đang xử lý = Tổng - (Mới + Đã phản hồi)
            int dangXuLy = rs.getInt("TongSo") - rs.getInt("SoMoi") - rs.getInt("SoDaPhanHoi");
            thongKe.setSoYKienDangXuLy(dangXuLy);
            
            return thongKe;
        });
    }
}