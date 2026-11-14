package com.example.ThamVanChinhSach.dto;

/**
 * Body cho API POST /api/duthao/{maDuThao}/lien-ket-thay-doi.
 * Mã dự thảo mới lấy từ đường dẫn; loaiThayDoi dùng để chọn phản hồi phù hợp.
 */
public class TaoLienKetTuDuThaoRequest {
    private int maYKien;
    private String moTaThayDoi;
    private String loaiThayDoi;

    public int getMaYKien() {
        return maYKien;
    }

    public void setMaYKien(int maYKien) {
        this.maYKien = maYKien;
    }

    public String getMoTaThayDoi() {
        return moTaThayDoi;
    }

    public void setMoTaThayDoi(String moTaThayDoi) {
        this.moTaThayDoi = moTaThayDoi;
    }

    public String getLoaiThayDoi() {
        return loaiThayDoi;
    }

    public void setLoaiThayDoi(String loaiThayDoi) {
        this.loaiThayDoi = loaiThayDoi;
    }
}
