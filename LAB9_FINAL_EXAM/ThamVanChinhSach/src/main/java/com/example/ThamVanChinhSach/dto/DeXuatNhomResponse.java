package com.example.ThamVanChinhSach.dto;

import java.util.List;

/**
 * Kết quả gợi ý phân nhóm do hệ thống đề xuất.
 * batBuocRaSoat luôn là true: đề xuất không thay thế quyết định của con người.
 */
public class DeXuatNhomResponse {
    private final int maYKien;
    private final Integer maNhomDeXuat;
    private final String tenNhomDeXuat;
    private final double doTinCay;
    private final List<String> tuKhoaKhop;
    private final boolean batBuocRaSoat;
    private final String ghiChu;

    public DeXuatNhomResponse(int maYKien,
                              Integer maNhomDeXuat,
                              String tenNhomDeXuat,
                              double doTinCay,
                              List<String> tuKhoaKhop,
                              String ghiChu) {
        this.maYKien = maYKien;
        this.maNhomDeXuat = maNhomDeXuat;
        this.tenNhomDeXuat = tenNhomDeXuat;
        this.doTinCay = doTinCay;
        this.tuKhoaKhop = tuKhoaKhop;
        this.batBuocRaSoat = true;
        this.ghiChu = ghiChu;
    }

    public int getMaYKien() {
        return maYKien;
    }

    public Integer getMaNhomDeXuat() {
        return maNhomDeXuat;
    }

    public String getTenNhomDeXuat() {
        return tenNhomDeXuat;
    }

    public double getDoTinCay() {
        return doTinCay;
    }

    /** Bí danh "độ tương đồng" mà giao diện đang dùng. */
    public double getDoTuongDong() {
        return doTinCay;
    }

    public List<String> getTuKhoaKhop() {
        return tuKhoaKhop;
    }

    public boolean isBatBuocRaSoat() {
        return batBuocRaSoat;
    }

    public String getGhiChu() {
        return ghiChu;
    }
}
