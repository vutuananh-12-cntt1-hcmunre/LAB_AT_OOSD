package com.example.quanlykhachsan.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChiTietPhieuSuDungDVId implements Serializable {

    private String soPhieuSDDV;
    private String maDV;

    public ChiTietPhieuSuDungDVId() {
    }

    public ChiTietPhieuSuDungDVId(String soPhieuSDDV, String maDV) {
        this.soPhieuSDDV = soPhieuSDDV;
        this.maDV = maDV;
    }

    public String getSoPhieuSDDV() {
        return soPhieuSDDV;
    }

    public void setSoPhieuSDDV(String soPhieuSDDV) {
        this.soPhieuSDDV = soPhieuSDDV;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof ChiTietPhieuSuDungDVId)) {
            return false;
        }

        ChiTietPhieuSuDungDVId that =
                (ChiTietPhieuSuDungDVId) o;

        return Objects.equals(soPhieuSDDV, that.soPhieuSDDV)
                && Objects.equals(maDV, that.maDV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(soPhieuSDDV, maDV);
    }
}