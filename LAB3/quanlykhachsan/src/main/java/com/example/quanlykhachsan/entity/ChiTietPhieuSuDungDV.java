package com.example.quanlykhachsan.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "ChiTietPhieuSuDungDV")
@IdClass(ChiTietPhieuSuDungDVId.class)
public class ChiTietPhieuSuDungDV {

    @Id
    @Column(name = "SoPhieuSDDV")
    private String soPhieuSDDV;

    @Id
    @Column(name = "MaDV")
    private String maDV;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia")
    private BigDecimal donGia;

    @Column(name = "ThanhTien", insertable = false, updatable = false)
    private BigDecimal thanhTien;

    public ChiTietPhieuSuDungDV() {
    }

    public String getSoPhieuSDDV() {
        return soPhieuSDDV;
    }

    public void setSoPhieuSDDV(String soPhieuSDDV) {
        this.soPhieuSDDV = soPhieuSDDV;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }
}