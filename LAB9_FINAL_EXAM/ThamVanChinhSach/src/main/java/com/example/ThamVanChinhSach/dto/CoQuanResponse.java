package com.example.ThamVanChinhSach.dto;

public class CoQuanResponse {
    private int maCoQuan;
    private String tenCoQuan;
    private String emailLienHe;
    private String soDienThoai;
    private String diaChi;
    private boolean trangThai;

    public int getMaCoQuan() {
        return maCoQuan;
    }

    public void setMaCoQuan(int maCoQuan) {
        this.maCoQuan = maCoQuan;
    }

    public String getTenCoQuan() {
        return tenCoQuan;
    }

    public void setTenCoQuan(String tenCoQuan) {
        this.tenCoQuan = tenCoQuan;
    }

    public String getEmailLienHe() {
        return emailLienHe;
    }

    public void setEmailLienHe(String emailLienHe) {
        this.emailLienHe = emailLienHe;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
