package com.example.ThamVanChinhSach.dto;

public class PhanHoiNhomRequest {
    private int maNhom;
    private String ketQuaXuLy;
    private String noiDungGiaiThich;

    public int getMaNhom() {
        return maNhom;
    }

    public void setMaNhom(int maNhom) {
        this.maNhom = maNhom;
    }

    public String getKetQuaXuLy() {
        return ketQuaXuLy;
    }

    public void setKetQuaXuLy(String ketQuaXuLy) {
        this.ketQuaXuLy = ketQuaXuLy;
    }

    public String getNoiDungGiaiThich() {
        return noiDungGiaiThich;
    }

    public void setNoiDungGiaiThich(String noiDungGiaiThich) {
        this.noiDungGiaiThich = noiDungGiaiThich;
    }
}
