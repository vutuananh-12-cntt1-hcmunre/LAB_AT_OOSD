package com.example.quanlykhachsan.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChiTietPhieuDenBuId implements Serializable {

    private String soPhieuDenBu;
    private String maTienNghi;

    public ChiTietPhieuDenBuId() {
    }

    public ChiTietPhieuDenBuId(String soPhieuDenBu, String maTienNghi) {
        this.soPhieuDenBu = soPhieuDenBu;
        this.maTienNghi = maTienNghi;
    }

    public String getSoPhieuDenBu() {
        return soPhieuDenBu;
    }

    public void setSoPhieuDenBu(String soPhieuDenBu) {
        this.soPhieuDenBu = soPhieuDenBu;
    }

    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChiTietPhieuDenBuId)) return false;

        ChiTietPhieuDenBuId that = (ChiTietPhieuDenBuId) o;

        return Objects.equals(soPhieuDenBu, that.soPhieuDenBu)
                && Objects.equals(maTienNghi, that.maTienNghi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(soPhieuDenBu, maTienNghi);
    }
}