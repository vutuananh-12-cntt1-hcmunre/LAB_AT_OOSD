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
@Table(name = "the_doc_gia")
public class TheDocGia {

    @Id
    @Column(name = "ma_the", length = 20)
    private String maThe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ma_doc_gia", nullable = false)
    private DocGia docGia;

    @Column(name = "ngay_cap", nullable = false)
    private LocalDate ngayCap;

    @Column(name = "han_su_dung", nullable = false)
    private LocalDate hanSuDung;

    @Column(name = "da_dong_le_phi", nullable = false)
    private Boolean daDongLePhi;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai;

    public TheDocGia() {
    }

    public TheDocGia(
            String maThe,
            DocGia docGia,
            LocalDate ngayCap,
            LocalDate hanSuDung,
            Boolean daDongLePhi,
            Boolean trangThai) {

        this.maThe = maThe;
        this.docGia = docGia;
        this.ngayCap = ngayCap;
        this.hanSuDung = hanSuDung;
        this.daDongLePhi = daDongLePhi;
        this.trangThai = trangThai;
    }

    public String getMaThe() {
        return maThe;
    }

    public void setMaThe(String maThe) {
        this.maThe = maThe;
    }

    public DocGia getDocGia() {
        return docGia;
    }

    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }

    public LocalDate getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(LocalDate ngayCap) {
        this.ngayCap = ngayCap;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public Boolean getDaDongLePhi() {
        return daDongLePhi;
    }

    public void setDaDongLePhi(Boolean daDongLePhi) {
        this.daDongLePhi = daDongLePhi;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}