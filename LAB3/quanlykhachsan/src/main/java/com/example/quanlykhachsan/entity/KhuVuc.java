package com.example.quanlykhachsan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "KhuVuc")
public class KhuVuc {

    @Id
    @Column(name = "MaKhuVuc")
    private String maKhuVuc;

    @Column(name = "TenKhuVuc")
    private String tenKhuVuc;

    public KhuVuc() {
    }

    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }
}