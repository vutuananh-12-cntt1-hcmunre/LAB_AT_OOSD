package com.example.quanlykhachsan.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PhieuDatPhong")
public class PhieuDatPhong {

    @Id
    @Column(name = "SoPhieuDat")
    private String soPhieuDat;

    @Column(name = "MaKhach")
    private String maKhach;

    @Column(name = "MaNVLeTan")
    private String maNVLeTan;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "NgayNhan")
    private LocalDate ngayNhan;

    @Column(name = "NgayTraDuKien")
    private LocalDate ngayTraDuKien;

    @Column(name = "TienCoc")
    private BigDecimal tienCoc;

    @Column(name = "KenhDat")
    private String kenhDat;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "NgayNhanThucTe")
    private LocalDateTime ngayNhanThucTe;

    @Column(name = "NgayTraThucTe")
    private LocalDateTime ngayTraThucTe;

    public PhieuDatPhong() {
    }

    public String getSoPhieuDat() {
        return soPhieuDat;
    }

    public void setSoPhieuDat(String soPhieuDat) {
        this.soPhieuDat = soPhieuDat;
    }

    public String getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(String maKhach) {
        this.maKhach = maKhach;
    }

    public String getMaNVLeTan() {
        return maNVLeTan;
    }

    public void setMaNVLeTan(String maNVLeTan) {
        this.maNVLeTan = maNVLeTan;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        this.ngayLap = ngayLap;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(LocalDate ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public LocalDate getNgayTraDuKien() {
        return ngayTraDuKien;
    }

    public void setNgayTraDuKien(LocalDate ngayTraDuKien) {
        this.ngayTraDuKien = ngayTraDuKien;
    }

    public BigDecimal getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(BigDecimal tienCoc) {
        this.tienCoc = tienCoc;
    }

    public String getKenhDat() {
        return kenhDat;
    }

    public void setKenhDat(String kenhDat) {
        this.kenhDat = kenhDat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getNgayNhanThucTe() {
        return ngayNhanThucTe;
    }

    public void setNgayNhanThucTe(LocalDateTime ngayNhanThucTe) {
        this.ngayNhanThucTe = ngayNhanThucTe;
    }

    public LocalDateTime getNgayTraThucTe() {
        return ngayTraThucTe;
    }

    public void setNgayTraThucTe(LocalDateTime ngayTraThucTe) {
        this.ngayTraThucTe = ngayTraThucTe;
    }
}