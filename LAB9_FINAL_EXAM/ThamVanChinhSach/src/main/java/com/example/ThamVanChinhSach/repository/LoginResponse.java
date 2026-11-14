package com.example.ThamVanChinhSach.repository;

public class LoginResponse {
    private int maNguoiDung;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String maVaiTro;
    private String tenVaiTro;
    private Integer maCoQuan;
    private String tenCoQuan;

    private String token;
    private String tokenType = "Bearer";

    public int getMaNguoiDung() {
        return maNguoiDung;
    }
    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }
    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public String getMaVaiTro() {
        return maVaiTro;
    }
    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }
    public String getTenVaiTro() {
        return tenVaiTro;
    }
    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
    public Integer getMaCoQuan() {
        return maCoQuan;
    }
    public void setMaCoQuan(Integer maCoQuan) {
        this.maCoQuan = maCoQuan;
    }
    public String getTenCoQuan() {
        return tenCoQuan;
    }
    public void setTenCoQuan(String tenCoQuan) {
        this.tenCoQuan = tenCoQuan;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
