package com.example.quanlykhachsan.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PhieuSuDungDV")
public class PhieuSuDungDV {

    @Id
    @Column(name = "SoPhieuSDDV")
    private String soPhieuSDDV;

    @Column(name = "SoPhieuDat")
    private String soPhieuDat;

    @Column(name = "SoPhong")
    private String soPhong;

    @Column(name = "NgaySuDung")
    private LocalDate ngaySuDung;

    @Column(name = "MaNV")
    private String maNV;

    public PhieuSuDungDV() {
    }

    public String getSoPhieuSDDV() {
        return soPhieuSDDV;
    }

    public void setSoPhieuSDDV(String soPhieuSDDV) {
        this.soPhieuSDDV = soPhieuSDDV;
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

    public LocalDate getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(LocalDate ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}