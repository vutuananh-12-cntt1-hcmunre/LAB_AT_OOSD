package com.example.ThamVanChinhSach.dto;
import java.time.LocalDateTime;

public class YKienResponse {
    private int maYKien;
    private int maChuDe;
    private int nguoiGui;
    private String noiDung;
    private LocalDateTime thoiDiemGui;
    private String trangThaiXuLy;
    public int getMaYKien() {
        return maYKien;
    }
    public void setMaYKien(int maYKien) {
        this.maYKien = maYKien;
    }
    public int getMaChuDe() {
        return maChuDe;
    }
    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }
    public int getNguoiGui() {
        return nguoiGui;
    }
    public void setNguoiGui(int nguoiGui) {
        this.nguoiGui = nguoiGui;
    }
    public String getNoiDung() {
        return noiDung;
    }
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    public LocalDateTime getThoiDiemGui() {
        return thoiDiemGui;
    }
    public void setThoiDiemGui(LocalDateTime thoiDiemGui) {
        this.thoiDiemGui = thoiDiemGui;
    }
    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }
    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }
}
