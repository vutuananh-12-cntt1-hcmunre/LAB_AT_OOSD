package com.example.ThamVanChinhSach.dto;


public class RegisterRequest {
    private String hoTen;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String diaChi;
    private String maVaiTro = "NGUOI_DAN";
    private Integer maCoQuan;

    public String getHoTen() {
        return hoTen;
    }

    public String getEmail() {
        return email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getMaVaiTro() {
        return maVaiTro;
    }

    public Integer getMaCoQuan() {
        return maCoQuan;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public void setMaCoQuan(Integer maCoQuan) {
        this.maCoQuan = maCoQuan;
    }
}