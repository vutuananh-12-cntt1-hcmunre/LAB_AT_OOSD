package com.example.quanlythuvien.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chi_tiet_phieu_muon")
public class ChiTietPhieuMuon {

    @Id
    @Column(name = "ma_chi_tiet", length = 50)
    private String maChiTiet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ma_phieu_muon",
            nullable = false
    )
    private PhieuMuon phieuMuon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ma_dau_sach",
            nullable = false
    )
    private DauSach dauSach;

    @Column(name = "ngay_tra")
    private LocalDate ngayTra;

    @Column(
            name = "tien_phat",
            precision = 18,
            scale = 2
    )
    private BigDecimal tienPhat;

    @Column(name = "ghi_chu", length = 255)
    private String ghiChu;

    public ChiTietPhieuMuon() {
    }

    public ChiTietPhieuMuon(
            String maChiTiet,
            PhieuMuon phieuMuon,
            DauSach dauSach,
            LocalDate ngayTra,
            BigDecimal tienPhat,
            String ghiChu) {

        this.maChiTiet = maChiTiet;
        this.phieuMuon = phieuMuon;
        this.dauSach = dauSach;
        this.ngayTra = ngayTra;
        this.tienPhat = tienPhat;
        this.ghiChu = ghiChu;
    }

    public String getMaChiTiet() {
        return maChiTiet;
    }

    public void setMaChiTiet(String maChiTiet) {
        this.maChiTiet = maChiTiet;
    }

    public PhieuMuon getPhieuMuon() {
        return phieuMuon;
    }

    public void setPhieuMuon(PhieuMuon phieuMuon) {
        this.phieuMuon = phieuMuon;
    }

    public DauSach getDauSach() {
        return dauSach;
    }

    public void setDauSach(DauSach dauSach) {
        this.dauSach = dauSach;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public BigDecimal getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(BigDecimal tienPhat) {
        this.tienPhat = tienPhat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}