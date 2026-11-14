package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

public class LichSuXuLyResponse {
    private long maLichSu;
    private Integer maYKien;
    private Integer maDuThao;
    private Integer maNguoiThucHien;
    private String tenNguoiThucHien;
    private String hanhDong;
    private LocalDateTime thoiDiem;
    private String ghiChu;

    public long getMaLichSu() {
        return maLichSu;
    }

    public void setMaLichSu(long maLichSu) {
        this.maLichSu = maLichSu;
    }

    public Integer getMaYKien() {
        return maYKien;
    }

    public void setMaYKien(Integer maYKien) {
        this.maYKien = maYKien;
    }

    public Integer getMaDuThao() {
        return maDuThao;
    }

    public void setMaDuThao(Integer maDuThao) {
        this.maDuThao = maDuThao;
    }

    public Integer getMaNguoiThucHien() {
        return maNguoiThucHien;
    }

    public void setMaNguoiThucHien(Integer maNguoiThucHien) {
        this.maNguoiThucHien = maNguoiThucHien;
    }

    public String getTenNguoiThucHien() {
        return tenNguoiThucHien;
    }

    public void setTenNguoiThucHien(String tenNguoiThucHien) {
        this.tenNguoiThucHien = tenNguoiThucHien;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public LocalDateTime getThoiDiem() {
        return thoiDiem;
    }

    public void setThoiDiem(LocalDateTime thoiDiem) {
        this.thoiDiem = thoiDiem;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
