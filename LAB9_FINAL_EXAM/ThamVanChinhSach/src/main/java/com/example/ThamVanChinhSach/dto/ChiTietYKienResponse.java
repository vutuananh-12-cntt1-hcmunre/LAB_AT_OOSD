package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChiTietYKienResponse {
    private int maYKien;
    private int maChuDe;
    private String noiDung;
    private LocalDateTime thoiDiemGui;
    private String trangThaiXuLy;
    private Integer maNhomAI;
    private Integer maNhomChinhThuc;
    private String tenNhomAI;
    private String tenNhomChinhThuc;
    private Integer nguoiRaSoat;
    private LocalDateTime thoiDiemRaSoat;
    private String ketQuaXuLy;
    private String noiDungGiaiThich;
    private LocalDateTime thoiDiemPhanHoi;
    private List<DuThaoTiepThuResponse> duThaoLienKet = new ArrayList<>();

    public int getMaYKien() {
        return maYKien;
    }

    public void setMaYKien(int maYKien) {
        this.maYKien = maYKien;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDateTime getThoiDiemGui() {
        return thoiDiemGui;
    }

    public void setThoiDiemGui(LocalDateTime thoiDiemGui) {
        this.thoiDiemGui = thoiDiemGui;
    }

    public String getTrangThaiXuLy() {
        return trangThaiXuLy;
    }

    public void setTrangThaiXuLy(String trangThaiXuLy) {
        this.trangThaiXuLy = trangThaiXuLy;
    }

    public Integer getMaNhomAI() {
        return maNhomAI;
    }

    public void setMaNhomAI(Integer maNhomAI) {
        this.maNhomAI = maNhomAI;
    }

    public Integer getMaNhomChinhThuc() {
        return maNhomChinhThuc;
    }

    public void setMaNhomChinhThuc(Integer maNhomChinhThuc) {
        this.maNhomChinhThuc = maNhomChinhThuc;
    }

    public String getTenNhomAI() {
        return tenNhomAI;
    }

    public void setTenNhomAI(String tenNhomAI) {
        this.tenNhomAI = tenNhomAI;
    }

    public String getTenNhomChinhThuc() {
        return tenNhomChinhThuc;
    }

    public void setTenNhomChinhThuc(String tenNhomChinhThuc) {
        this.tenNhomChinhThuc = tenNhomChinhThuc;
    }

    public Integer getNguoiRaSoat() {
        return nguoiRaSoat;
    }

    public void setNguoiRaSoat(Integer nguoiRaSoat) {
        this.nguoiRaSoat = nguoiRaSoat;
    }

    public LocalDateTime getThoiDiemRaSoat() {
        return thoiDiemRaSoat;
    }

    public void setThoiDiemRaSoat(LocalDateTime thoiDiemRaSoat) {
        this.thoiDiemRaSoat = thoiDiemRaSoat;
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

    public LocalDateTime getThoiDiemPhanHoi() {
        return thoiDiemPhanHoi;
    }

    public void setThoiDiemPhanHoi(LocalDateTime thoiDiemPhanHoi) {
        this.thoiDiemPhanHoi = thoiDiemPhanHoi;
    }

    public List<DuThaoTiepThuResponse> getDuThaoLienKet() {
        return duThaoLienKet;
    }

    public void setDuThaoLienKet(List<DuThaoTiepThuResponse> duThaoLienKet) {
        this.duThaoLienKet = duThaoLienKet;
    }
}
