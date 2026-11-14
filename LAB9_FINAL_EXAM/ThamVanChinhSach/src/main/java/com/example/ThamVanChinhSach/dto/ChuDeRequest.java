package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

public class ChuDeRequest {
    private int maCoQuan;
    private String tieuDe;
    private String noiDungMoTa;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    public int getMaCoQuan() {
        return maCoQuan;
    }
    public void setMaCoQuan(int maCoQuan) {
        this.maCoQuan = maCoQuan;
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
}
