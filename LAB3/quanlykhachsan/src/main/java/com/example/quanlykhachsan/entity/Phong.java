package com.example.quanlykhachsan.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Phong")
public class Phong {

    @Id
    @Column(name = "SoPhong")
    private String soPhong;

    @Column(name = "MaKhuVuc")
    private String maKhuVuc;

    @Column(name = "SoNguoiToiDa")
    private Integer soNguoiToiDa;

    @Column(name = "DonGiaNgay")
    private BigDecimal donGiaNgay;

    @Column(name = "TrangThai")
    private String trangThai;

    public Phong() {
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public Integer getSoNguoiToiDa() {
        return soNguoiToiDa;
    }

    public void setSoNguoiToiDa(Integer soNguoiToiDa) {
        this.soNguoiToiDa = soNguoiToiDa;
    }

    public BigDecimal getDonGiaNgay() {
        return donGiaNgay;
    }

    public void setDonGiaNgay(BigDecimal donGiaNgay) {
        this.donGiaNgay = donGiaNgay;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}