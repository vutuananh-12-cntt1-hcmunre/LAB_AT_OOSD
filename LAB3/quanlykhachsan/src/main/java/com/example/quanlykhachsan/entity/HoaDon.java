package com.example.quanlykhachsan.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HoaDon")
public class HoaDon {

    @Id
    @Column(name = "SoHoaDon")
    private String soHoaDon;

    @Column(name = "SoPhieuDat")
    private String soPhieuDat;

    @Column(name = "NgayLap")
    private LocalDateTime ngayLap;

    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "SoNgayTinhTien")
    private Integer soNgayTinhTien;

    @Column(name = "TienPhong")
    private BigDecimal tienPhong;

    @Column(name = "TienDichVu")
    private BigDecimal tienDichVu;

    // SQL Server tự động tính cột này
    @Column(name = "TongTien", insertable = false, updatable = false)
    private BigDecimal tongTien;

    @Column(name = "TrangThai")
    private String trangThai;

    public HoaDon() {
    }

    public String getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(String soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public String getSoPhieuDat() {
        return soPhieuDat;
    }

    public void setSoPhieuDat(String soPhieuDat) {
        this.soPhieuDat = soPhieuDat;
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

    public Integer getSoNgayTinhTien() {
        return soNgayTinhTien;
    }

    public void setSoNgayTinhTien(Integer soNgayTinhTien) {
        this.soNgayTinhTien = soNgayTinhTien;
    }

    public BigDecimal getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(BigDecimal tienPhong) {
        this.tienPhong = tienPhong;
    }

    public BigDecimal getTienDichVu() {
        return tienDichVu;
    }

    public void setTienDichVu(BigDecimal tienDichVu) {
        this.tienDichVu = tienDichVu;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}