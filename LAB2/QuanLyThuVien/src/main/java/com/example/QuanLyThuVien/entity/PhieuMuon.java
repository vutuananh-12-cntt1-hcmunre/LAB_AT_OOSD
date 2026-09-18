package com.example.quanlythuvien.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "phieu_muon")
public class PhieuMuon {

    @Id
    @Column(name = "ma_phieu_muon", length = 50)
    private String maPhieuMuon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ma_doc_gia",
            nullable = false
    )
    private DocGia docGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ma_nhan_vien",
            nullable = false
    )
    private NhanVien nhanVien;

    @Column(name = "ngay_muon", nullable = false)
    private LocalDate ngayMuon;

    @Column(name = "ngay_hen_tra", nullable = false)
    private LocalDate ngayHenTra;

    public PhieuMuon() {
    }

    public PhieuMuon(
            String maPhieuMuon,
            DocGia docGia,
            NhanVien nhanVien,
            LocalDate ngayMuon,
            LocalDate ngayHenTra) {

        this.maPhieuMuon = maPhieuMuon;
        this.docGia = docGia;
        this.nhanVien = nhanVien;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public DocGia getDocGia() {
        return docGia;
    }

    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalDate getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(LocalDate ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public LocalDate getNgayHenTra() {
        return ngayHenTra;
    }

    public void setNgayHenTra(LocalDate ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }
}