package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

public class TaiLieuResponse {
    private int maTaiLieu;
    private int maYKien;
    private String tenTep;
    private String duongDanTep;
    private String loaiTep;
    private Long kichThuoc;
    private LocalDateTime thoiDiemTaiLen;

    public int getMaTaiLieu() {
        return maTaiLieu;
    }

    public void setMaTaiLieu(int maTaiLieu) {
        this.maTaiLieu = maTaiLieu;
    }

    public int getMaYKien() {
        return maYKien;
    }

    public void setMaYKien(int maYKien) {
        this.maYKien = maYKien;
    }

    public String getTenTep() {
        return tenTep;
    }

    public void setTenTep(String tenTep) {
        this.tenTep = tenTep;
    }

    public String getDuongDanTep() {
        return duongDanTep;
    }

    public void setDuongDanTep(String duongDanTep) {
        this.duongDanTep = duongDanTep;
    }

    public String getLoaiTep() {
        return loaiTep;
    }

    public void setLoaiTep(String loaiTep) {
        this.loaiTep = loaiTep;
    }

    public Long getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(Long kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public LocalDateTime getThoiDiemTaiLen() {
        return thoiDiemTaiLen;
    }

    public void setThoiDiemTaiLen(LocalDateTime thoiDiemTaiLen) {
        this.thoiDiemTaiLen = thoiDiemTaiLen;
    }
}
