package com.example.quanlykhachsan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LoaiTienNghi")
public class LoaiTienNghi {

    @Id
    @Column(name = "MaLoaiTN")
    private String maLoaiTN;

    @Column(name = "TenLoaiTN")
    private String tenLoaiTN;

    public LoaiTienNghi() {
    }

    public String getMaLoaiTN() {
        return maLoaiTN;
    }

    public void setMaLoaiTN(String maLoaiTN) {
        this.maLoaiTN = maLoaiTN;
    }

    public String getTenLoaiTN() {
        return tenLoaiTN;
    }

    public void setTenLoaiTN(String tenLoaiTN) {
        this.tenLoaiTN = tenLoaiTN;
    }
}