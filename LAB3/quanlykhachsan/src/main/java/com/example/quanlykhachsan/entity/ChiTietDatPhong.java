package com.example.quanlykhachsan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "ChiTietDatPhong")
@IdClass(ChiTietDatPhongId.class)
public class ChiTietDatPhong {

    @Id
    @Column(name = "SoPhieuDat")
    private String soPhieuDat;

    @Id
    @Column(name = "SoPhong")
    private String soPhong;

    @Column(name = "SoNguoi")
    private Integer soNguoi;

    public ChiTietDatPhong() {
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

    public Integer getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(Integer soNguoi) {
        this.soNguoi = soNguoi;
    }
}