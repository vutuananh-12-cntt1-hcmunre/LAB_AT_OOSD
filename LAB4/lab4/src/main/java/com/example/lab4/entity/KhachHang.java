package com.example.lab4.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "KHACH_HANG")
public class KhachHang {

    @Id
    @Column(name = "MaKH", length = 10)
    private String maKH;

    @Column(name = "HoTen", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "CCCD_Passport", length = 30)
    private String cccdPassport;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @Column(name = "DienThoai", length = 20)
    private String dienThoai;

    @Column(name = "Username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "MatKhau", nullable = false, length = 100)
    private String matKhau;

    @Column(name = "Email", unique = true, length = 100)
    private String email;

    public KhachHang() {
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getCccdPassport() {
        return cccdPassport;
    }

    public void setCccdPassport(String cccdPassport) {
        this.cccdPassport = cccdPassport;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}