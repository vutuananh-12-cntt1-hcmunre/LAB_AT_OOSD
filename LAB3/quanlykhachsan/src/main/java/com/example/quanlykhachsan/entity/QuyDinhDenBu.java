package com.example.quanlykhachsan.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "QuyDinhDenBu")
public class QuyDinhDenBu {

    @Id
    @Column(name = "MaQuyDinh")
    private String maQuyDinh;

    @Column(name = "MaLoaiTN")
    private String maLoaiTN;

    @Column(name = "MucDoThietHai")
    private String mucDoThietHai;

    @Column(name = "MucDenBu")
    private BigDecimal mucDenBu;

    public QuyDinhDenBu() {
    }

    public String getMaQuyDinh() {
        return maQuyDinh;
    }

    public void setMaQuyDinh(String maQuyDinh) {
        this.maQuyDinh = maQuyDinh;
    }

    public String getMaLoaiTN() {
        return maLoaiTN;
    }

    public void setMaLoaiTN(String maLoaiTN) {
        this.maLoaiTN = maLoaiTN;
    }

    public String getMucDoThietHai() {
        return mucDoThietHai;
    }

    public void setMucDoThietHai(String mucDoThietHai) {
        this.mucDoThietHai = mucDoThietHai;
    }

    public BigDecimal getMucDenBu() {
        return mucDenBu;
    }

    public void setMucDenBu(BigDecimal mucDenBu) {
        this.mucDenBu = mucDenBu;
    }
}