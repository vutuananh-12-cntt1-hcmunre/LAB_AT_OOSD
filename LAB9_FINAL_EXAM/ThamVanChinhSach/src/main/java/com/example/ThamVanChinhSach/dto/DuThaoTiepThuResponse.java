package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

/** Một phiên bản dự thảo đã tiếp thu ý kiến (chiều Ý kiến -> Dự thảo). */
public class DuThaoTiepThuResponse {
    private int maLienKet;
    private int maDuThao;
    private String tenDuThao;
    private String phienBan;
    private String trangThaiDuThao;
    private LocalDateTime thoiDiemTao;
    private String noiDungThayDoi;

    public int getMaLienKet() {
        return maLienKet;
    }

    public void setMaLienKet(int maLienKet) {
        this.maLienKet = maLienKet;
    }

    public int getMaDuThao() {
        return maDuThao;
    }

    public void setMaDuThao(int maDuThao) {
        this.maDuThao = maDuThao;
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

    public String getTrangThaiDuThao() {
        return trangThaiDuThao;
    }

    public void setTrangThaiDuThao(String trangThaiDuThao) {
        this.trangThaiDuThao = trangThaiDuThao;
    }

    /** Bí danh cho giao diện chi tiết ý kiến. */
    public String getTrangThai() {
        return trangThaiDuThao;
    }

    public LocalDateTime getThoiDiemTao() {
        return thoiDiemTao;
    }

    public void setThoiDiemTao(LocalDateTime thoiDiemTao) {
        this.thoiDiemTao = thoiDiemTao;
    }

    public String getNoiDungThayDoi() {
        return noiDungThayDoi;
    }

    public void setNoiDungThayDoi(String noiDungThayDoi) {
        this.noiDungThayDoi = noiDungThayDoi;
    }

    /** Bí danh cho giao diện chi tiết ý kiến. */
    public String getMoTaThayDoi() {
        return noiDungThayDoi;
    }
}
