package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

public class DuThaoResponse {
    private int maDuThao;
    private int maChuDe;
    private String tenDuThao;
    private String phienBan;
    private String noiDung;
    private String trangThai;
    private LocalDateTime thoiDiemTao;
    private int nguoiCapNhat;
    private Integer maDuThaoTruoc;
    private String ghiChuThayDoi;

    public int getMaDuThao() {
        return maDuThao;
    }

    public void setMaDuThao(int maDuThao) {
        this.maDuThao = maDuThao;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }

    public String getTenDuThao() {
        return tenDuThao;
    }

    public void setTenDuThao(String tenDuThao) {
        this.tenDuThao = tenDuThao;
    }

    public String getPhienBan() {
        return phienBan;
    }

    public void setPhienBan(String phienBan) {
        this.phienBan = phienBan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getThoiDiemTao() {
        return thoiDiemTao;
    }

    public void setThoiDiemTao(LocalDateTime thoiDiemTao) {
        this.thoiDiemTao = thoiDiemTao;
    }

    public int getNguoiCapNhat() {
        return nguoiCapNhat;
    }

    public void setNguoiCapNhat(int nguoiCapNhat) {
        this.nguoiCapNhat = nguoiCapNhat;
    }

    public Integer getMaDuThaoTruoc() {
        return maDuThaoTruoc;
    }

    public void setMaDuThaoTruoc(Integer maDuThaoTruoc) {
        this.maDuThaoTruoc = maDuThaoTruoc;
    }

    public String getGhiChuThayDoi() {
        return ghiChuThayDoi;
    }

    public void setGhiChuThayDoi(String ghiChuThayDoi) {
        this.ghiChuThayDoi = ghiChuThayDoi;
    }
}
