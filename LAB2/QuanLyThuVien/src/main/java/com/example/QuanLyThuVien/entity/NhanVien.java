package com.example.quanlythuvien.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nhan_vien")
public class NhanVien {

    @Id
    @Column(name = "ma_nhan_vien", length = 20)
    private String maNhanVien;

    @Column(name = "ho", nullable = false, length = 50)
    private String ho;

    @Column(name = "ten", nullable = false, length = 50)
    private String ten;

    @Column(name = "phai", nullable = false, length = 10)
    private String phai;

    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "chuc_vu", nullable = false, length = 80)
    private String chucVu;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    // Constructor rỗng
    public NhanVien() {
    }

    // Constructor đầy đủ
    public NhanVien(
            String maNhanVien,
            String ho,
            String ten,
            String phai,
            LocalDate ngaySinh,
            String chucVu,
            String soDienThoai) {

        this.maNhanVien = maNhanVien;
        this.ho = ho;
        this.ten = ten;
        this.phai = phai;
        this.ngaySinh = ngaySinh;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
    }

    // Getter + Setter

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getPhai() {
        return phai;
    }

    public void setPhai(String phai) {
        this.phai = phai;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}