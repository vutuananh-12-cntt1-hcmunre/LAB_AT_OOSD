package com.example.ThamVanChinhSach.domain;

/**
 * Nhóm chủ đề dùng để phân loại ý kiến trong một chủ đề tham vấn.
 * Nhóm chỉ có ý nghĩa trong phạm vi chủ đề của nó (MaChuDe).
 */
public class NhomYKien {

    private final int maNhom;
    private final int maChuDe;
    private final String tenNhom;
    private final String moTa;

    public NhomYKien(int maNhom, int maChuDe, String tenNhom, String moTa) {
        if (maNhom <= 0) {
            throw new ViPhamNghiepVuException("Mã nhóm ý kiến không hợp lệ.");
        }
        if (maChuDe <= 0) {
            throw new ViPhamNghiepVuException("Nhóm ý kiến phải thuộc một chủ đề tham vấn.");
        }
        if (tenNhom == null || tenNhom.trim().isEmpty()) {
            throw new ViPhamNghiepVuException("Tên nhóm ý kiến không được để trống.");
        }
        this.maNhom = maNhom;
        this.maChuDe = maChuDe;
        this.tenNhom = tenNhom;
        this.moTa = moTa;
    }

    public int getMaNhom() {
        return maNhom;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public String getMoTa() {
        return moTa;
    }
}
