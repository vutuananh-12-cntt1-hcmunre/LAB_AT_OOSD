package com.example.quanlykhachsan.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "ChiTietPhieuDenBu")
@IdClass(ChiTietPhieuDenBuId.class)
public class ChiTietPhieuDenBu {

    @Id
    @Column(name = "SoPhieuDenBu")
    private String soPhieuDenBu;

    @Id
    @Column(name = "MaTienNghi")
    private String maTienNghi;

    @Column(name = "MucDoThietHai")
    private String mucDoThietHai;

    @Column(name = "SoTien")
    private BigDecimal soTien;

    public ChiTietPhieuDenBu() {
    }

    public String getSoPhieuDenBu() {
        return soPhieuDenBu;
    }

    public void setSoPhieuDenBu(String soPhieuDenBu) {
        this.soPhieuDenBu = soPhieuDenBu;
    }

    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public String getMucDoThietHai() {
        return mucDoThietHai;
    }

    public void setMucDoThietHai(String mucDoThietHai) {
        this.mucDoThietHai = mucDoThietHai;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }
}