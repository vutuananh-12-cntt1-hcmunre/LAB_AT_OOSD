package com.example.quanlythuvien.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nha_xuat_ban")
public class NhaXuatBan {

    @Id
    @Column(name = "ma_nha_xuat_ban", length = 20)
    private String maNhaXuatBan;

    @Column(name = "ten_nha_xuat_ban", nullable = false, length = 150)
    private String tenNhaXuatBan;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    public NhaXuatBan() {
    }

    public NhaXuatBan(
            String maNhaXuatBan,
            String tenNhaXuatBan,
            String diaChi,
            String soDienThoai) {

        this.maNhaXuatBan = maNhaXuatBan;
        this.tenNhaXuatBan = tenNhaXuatBan;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public String getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(String maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public String getTenNhaXuatBan() {
        return tenNhaXuatBan;
    }

    public void setTenNhaXuatBan(String tenNhaXuatBan) {
        this.tenNhaXuatBan = tenNhaXuatBan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}