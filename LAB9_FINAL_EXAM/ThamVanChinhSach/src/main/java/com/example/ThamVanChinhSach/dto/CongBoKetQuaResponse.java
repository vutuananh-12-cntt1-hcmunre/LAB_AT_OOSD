package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

public class CongBoKetQuaResponse {
    private int maCongBo;
    private int maChuDe;
    private int maDuThao;
    private int nguoiCongBo;
    private LocalDateTime thoiDiemCongBo;
    private String noiDungCongBo;
    private String tenChuDe;
    private String tenDuThao;
    private String phienBan;

    public int getMaCongBo() {
        return maCongBo;
    }

    public void setMaCongBo(int maCongBo) {
        this.maCongBo = maCongBo;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }

    public int getMaDuThao() {
        return maDuThao;
    }

    public void setMaDuThao(int maDuThao) {
        this.maDuThao = maDuThao;
    }

    public int getNguoiCongBo() {
        return nguoiCongBo;
    }

    public void setNguoiCongBo(int nguoiCongBo) {
        this.nguoiCongBo = nguoiCongBo;
    }

    public LocalDateTime getThoiDiemCongBo() {
        return thoiDiemCongBo;
    }

    public void setThoiDiemCongBo(LocalDateTime thoiDiemCongBo) {
        this.thoiDiemCongBo = thoiDiemCongBo;
    }

    public String getNoiDungCongBo() {
        return noiDungCongBo;
    }

    public void setNoiDungCongBo(String noiDungCongBo) {
        this.noiDungCongBo = noiDungCongBo;
    }

    public String getTenChuDe() {
        return tenChuDe;
    }

    public void setTenChuDe(String tenChuDe) {
        this.tenChuDe = tenChuDe;
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
}
