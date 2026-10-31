package com.example.lab4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SAN_PHAM")
public class SanPham {

    @Id
    @Column(name = "MaSP", length = 20)
    private String maSP;

    @Column(name = "TenSP", nullable = false, length = 200)
    private String tenSP;

    @Column(name = "NhaSanXuat", length = 100)
    private String nhaSanXuat;

    @Column(name = "HinhAnh", length = 500)
    private String hinhAnh;

    @Column(name = "MoTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "ThongSoKyThuat", columnDefinition = "NVARCHAR(MAX)")
    private String thongSoKyThuat;

    @Column(name = "GiaBan", nullable = false)
    private double giaBan;

    @Column(name = "ConHang")
    private boolean conHang;

    @Column(name = "MaNhom", length = 20)
    private String maNhom;

    @Column(name = "TenNhom", length = 200)
    private String tenNhom;

    public SanPham() {
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThongSoKyThuat() {
        return thongSoKyThuat;
    }

    public void setThongSoKyThuat(String thongSoKyThuat) {
        this.thongSoKyThuat = thongSoKyThuat;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public boolean isConHang() {
        return conHang;
    }

    public void setConHang(boolean conHang) {
        this.conHang = conHang;
    }

    public String getMaNhom() {
        return maNhom;
    }

    public void setMaNhom(String maNhom) {
        this.maNhom = maNhom;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }
}