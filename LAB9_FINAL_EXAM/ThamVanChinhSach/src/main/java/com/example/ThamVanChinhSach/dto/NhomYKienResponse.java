package com.example.ThamVanChinhSach.dto;

public class NhomYKienResponse {
    private int maNhom;
    private int maChuDe;
    private String tenNhom;
    private String moTa;
    private int soYKien;

    public int getMaNhom() {
        return maNhom;
    }

    public void setMaNhom(int maNhom) {
        this.maNhom = maNhom;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoYKien() {
        return soYKien;
    }

    public void setSoYKien(int soYKien) {
        this.soYKien = soYKien;
    }
}
