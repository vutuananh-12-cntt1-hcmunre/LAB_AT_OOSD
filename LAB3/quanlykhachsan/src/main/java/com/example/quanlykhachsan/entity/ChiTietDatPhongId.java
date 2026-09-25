package com.example.quanlykhachsan.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChiTietDatPhongId implements Serializable {

    private String soPhieuDat;
    private String soPhong;

    public ChiTietDatPhongId() {
    }

    public ChiTietDatPhongId(String soPhieuDat, String soPhong) {
        this.soPhieuDat = soPhieuDat;
        this.soPhong = soPhong;
    }

    public String getSoPhieuDat() {
        return soPhieuDat;
    }

    public void setSoPhieuDat(String soPhieuDat) {
        this.soPhieuDat = soPhieuDat;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof ChiTietDatPhongId)) {
            return false;
        }

        ChiTietDatPhongId that = (ChiTietDatPhongId) o;

        return Objects.equals(soPhieuDat, that.soPhieuDat)
                && Objects.equals(soPhong, that.soPhong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(soPhieuDat, soPhong);
    }
}