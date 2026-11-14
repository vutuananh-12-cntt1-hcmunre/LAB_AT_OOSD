package com.example.ThamVanChinhSach.dto;

public class CapNhatNguoiDungRequest {
    private String maVaiTro;
    private Integer maCoQuan;
    private Boolean trangThai;

    public String getMaVaiTro() {
        return maVaiTro;
    }

    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public Integer getMaCoQuan() {
        return maCoQuan;
    }

    public void setMaCoQuan(Integer maCoQuan) {
        this.maCoQuan = maCoQuan;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
