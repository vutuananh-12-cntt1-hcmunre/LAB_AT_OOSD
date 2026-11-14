package com.example.ThamVanChinhSach.dto;

public class CoQuanRequest {
    private String tenCoQuan;
    private String emailLienHe;
    private String soDienThoai;
    private String diaChi;

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
}
