package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

public class ChuDeResponse {
    private int maChuDe;
    private String tieuDe;
    private String noiDungMoTa;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    private String trangThai;
    public int getMaChuDe() {
        return maChuDe;
    }
    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }
    public String getTieuDe() {
        return tieuDe;
    }
    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }
    public String getNoiDungMoTa() {
        return noiDungMoTa;
    }
    public void setNoiDungMoTa(String noiDungMoTa) {
        this.noiDungMoTa = noiDungMoTa;
    }
    public LocalDateTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }
    public void setThoiGianBatDau(LocalDateTime thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }
    public LocalDateTime getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }
    public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
