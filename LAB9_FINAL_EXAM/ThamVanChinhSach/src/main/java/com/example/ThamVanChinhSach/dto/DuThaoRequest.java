package com.example.ThamVanChinhSach.dto;

public class DuThaoRequest {
    private int maChuDe;
    private String tenDuThao;
    private String phienBan;
    private String noiDung;
    private Integer maDuThaoTruoc;
    private String ghiChuThayDoi;

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
