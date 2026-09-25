package com.example.quanlykhachsan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NguoiLuuTru")
public class NguoiLuuTru {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNguoiLT")
    private Integer maNguoiLT;

    @Column(name = "SoPhieuDat")
    private String soPhieuDat;

    @Column(name = "SoPhong")
    private String soPhong;

    @Column(name = "HoTen")
    private String hoTen;

    @Column(name = "SoCMND")
    private String soCMND;

    @Column(name = "QuocTich")
    private String quocTich;

    public NguoiLuuTru() {
    }

    public Integer getMaNguoiLT() {
        return maNguoiLT;
    }

    public void setMaNguoiLT(Integer maNguoiLT) {
        this.maNguoiLT = maNguoiLT;
    }

    public String getSoPhieuDat() {
        return soPhieuDat;
    }

    public void setSoPhieuDat(String soPhieuDat) {
        this.soPhieuDat = soPhieuDat;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }
}