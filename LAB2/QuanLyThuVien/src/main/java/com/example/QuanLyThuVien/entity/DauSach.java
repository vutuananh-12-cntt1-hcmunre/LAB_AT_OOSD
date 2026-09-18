package com.example.quanlythuvien.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dau_sach")
public class DauSach {

    @Id
    @Column(name = "ma_dau_sach", length = 20)
    private String maDauSach;

    @Column(name = "ten_sach", nullable = false, length = 200)
    private String tenSach;

    @Column(name = "nam_xuat_ban")
    private Integer namXuatBan;

    @Column(name = "so_luong_hien_co", nullable = false)
    private Integer soLuongHienCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ma_the_loai",
            nullable = false
    )
    private TheLoai theLoai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ma_nha_xuat_ban",
            nullable = false
    )
    private NhaXuatBan nhaXuatBan;

    public DauSach() {
    }

    public DauSach(
            String maDauSach,
            String tenSach,
            Integer namXuatBan,
            Integer soLuongHienCo,
            TheLoai theLoai,
            NhaXuatBan nhaXuatBan) {

        this.maDauSach = maDauSach;
        this.tenSach = tenSach;
        this.namXuatBan = namXuatBan;
        this.soLuongHienCo = soLuongHienCo;
        this.theLoai = theLoai;
        this.nhaXuatBan = nhaXuatBan;
    }

    public String getMaDauSach() {
        return maDauSach;
    }

    public void setMaDauSach(String maDauSach) {
        this.maDauSach = maDauSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public Integer getSoLuongHienCo() {
        return soLuongHienCo;
    }

    public void setSoLuongHienCo(Integer soLuongHienCo) {
        this.soLuongHienCo = soLuongHienCo;
    }

    public TheLoai getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(TheLoai theLoai) {
        this.theLoai = theLoai;
    }

    public NhaXuatBan getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(NhaXuatBan nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }
}