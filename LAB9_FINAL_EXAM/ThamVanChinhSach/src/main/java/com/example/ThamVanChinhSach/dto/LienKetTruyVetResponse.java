package com.example.ThamVanChinhSach.dto;

import java.time.LocalDateTime;

/** Một ý kiến được liên kết làm căn cứ cho phiên bản dự thảo (chiều Dự thảo -> Ý kiến). */
public class LienKetTruyVetResponse {
    private int maLienKet;
    private int maYKien;
    private String noiDungYKien;
    private int nguoiGui;
    private String tenNguoiGui;
    private String trangThaiYKien;
    private Integer nguoiRaSoat;
    private String tenNguoiRaSoat;
    private LocalDateTime thoiDiemRaSoat;
    private String tenNhomChinhThuc;
    private Integer maPhanHoi;
    private String ketQuaXuLy;
    private String noiDungGiaiThich;
    private LocalDateTime thoiDiemPhanHoi;
    private String noiDungThayDoi;

    public int getMaLienKet() {
        return maLienKet;
    }

    public void setMaLienKet(int maLienKet) {
        this.maLienKet = maLienKet;
    }

    public int getMaYKien() {
        return maYKien;
    }

    public void setMaYKien(int maYKien) {
        this.maYKien = maYKien;
    }

    public String getNoiDungYKien() {
        return noiDungYKien;
    }

    public void setNoiDungYKien(String noiDungYKien) {
        this.noiDungYKien = noiDungYKien;
    }

    /** Bí danh ngắn gọn cho giao diện chi tiết dự thảo. */
    public String getNoiDung() {
        return noiDungYKien;
    }

    public int getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(int nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getTrangThaiYKien() {
        return trangThaiYKien;
    }

    public void setTrangThaiYKien(String trangThaiYKien) {
        this.trangThaiYKien = trangThaiYKien;
    }

    public Integer getNguoiRaSoat() {
        return nguoiRaSoat;
    }

    public void setNguoiRaSoat(Integer nguoiRaSoat) {
        this.nguoiRaSoat = nguoiRaSoat;
    }

    public String getTenNguoiRaSoat() {
        return tenNguoiRaSoat;
    }

    public void setTenNguoiRaSoat(String tenNguoiRaSoat) {
        this.tenNguoiRaSoat = tenNguoiRaSoat;
    }

    public LocalDateTime getThoiDiemRaSoat() {
        return thoiDiemRaSoat;
    }

    public void setThoiDiemRaSoat(LocalDateTime thoiDiemRaSoat) {
        this.thoiDiemRaSoat = thoiDiemRaSoat;
    }

    public String getTenNhomChinhThuc() {
        return tenNhomChinhThuc;
    }

    public void setTenNhomChinhThuc(String tenNhomChinhThuc) {
        this.tenNhomChinhThuc = tenNhomChinhThuc;
    }

    public Integer getMaPhanHoi() {
        return maPhanHoi;
    }

    public void setMaPhanHoi(Integer maPhanHoi) {
        this.maPhanHoi = maPhanHoi;
    }

    public String getKetQuaXuLy() {
        return ketQuaXuLy;
    }

    public void setKetQuaXuLy(String ketQuaXuLy) {
        this.ketQuaXuLy = ketQuaXuLy;
    }

    /** Bí danh "loại tiếp thu" cho bảng truy vết của dự thảo. */
    public String getLoaiThayDoi() {
        return ketQuaXuLy;
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

    public String getNoiDungThayDoi() {
        return noiDungThayDoi;
    }

    public void setNoiDungThayDoi(String noiDungThayDoi) {
        this.noiDungThayDoi = noiDungThayDoi;
    }

    /** Bí danh "mô tả thay đổi" cho bảng truy vết của dự thảo. */
    public String getMoTaThayDoi() {
        return noiDungThayDoi;
    }
}
