package com.example.quanlykhachsan.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PhieuLapDat")
public class PhieuLapDat {

    @Id
    @Column(name = "SoPhieuLapDat")
    private String soPhieuLapDat;

    @Column(name = "MaTienNghi")
    private String maTienNghi;

    @Column(name = "SoPhong")
    private String soPhong;

    @Column(name = "NgayLap")
    private LocalDate ngayLap;

    @Column(name = "TinhTrang")
    private String tinhTrang;

    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "GhiChu")
    private String ghiChu;

    public PhieuLapDat() {
    }

    public String getSoPhieuLapDat() {
        return soPhieuLapDat;
    }

    public void setSoPhieuLapDat(String soPhieuLapDat) {
        this.soPhieuLapDat = soPhieuLapDat;
    }

    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}