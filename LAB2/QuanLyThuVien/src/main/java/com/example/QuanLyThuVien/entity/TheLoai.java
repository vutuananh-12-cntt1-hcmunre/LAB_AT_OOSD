package com.example.quanlythuvien.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "the_loai")
public class TheLoai {

    @Id
    @Column(name = "ma_the_loai", length = 20)
    private String maTheLoai;

    @Column(name = "ten_the_loai", nullable = false, length = 100)
    private String tenTheLoai;

    public TheLoai() {
    }

    public TheLoai(String maTheLoai, String tenTheLoai) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
}