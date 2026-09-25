package com.example.quanlykhachsan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TienNghi")
public class TienNghi {

    @Id
    @Column(name = "MaTienNghi")
    private String maTienNghi;

    @Column(name = "MaLoaiTN")
    private String maLoaiTN;

    @Column(name = "SoThuTu")
    private Integer soThuTu;

    @Column(name = "TinhTrangHienTai")
    private String tinhTrangHienTai;

    public TienNghi() {
    }

    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public String getMaLoaiTN() {
        return maLoaiTN;
    }

    public void setMaLoaiTN(String maLoaiTN) {
        this.maLoaiTN = maLoaiTN;
    }

    public Integer getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(Integer soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getTinhTrangHienTai() {
        return tinhTrangHienTai;
    }

    public void setTinhTrangHienTai(String tinhTrangHienTai) {
        this.tinhTrangHienTai = tinhTrangHienTai;
    }
}