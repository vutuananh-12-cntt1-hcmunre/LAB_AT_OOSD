package com.example.quanlykhachsan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PhieuDenBu")
public class PhieuDenBu {

    @Id
    @Column(name = "SoPhieuDenBu")
    private String soPhieuDenBu;

    @Column(name = "SoPhieuDat")
    private String soPhieuDat;

    @Column(name = "SoPhong")
    private String soPhong;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "TongTien")
    private BigDecimal tongTien;

    public PhieuDenBu() {
    }

    public String getSoPhieuDenBu() {
        return soPhieuDenBu;
    }

    public void setSoPhieuDenBu(String soPhieuDenBu) {
        this.soPhieuDenBu = soPhieuDenBu;
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

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
}