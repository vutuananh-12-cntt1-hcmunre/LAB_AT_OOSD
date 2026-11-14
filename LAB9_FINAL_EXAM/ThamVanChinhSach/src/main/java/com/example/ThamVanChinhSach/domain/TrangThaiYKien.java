package com.example.ThamVanChinhSach.domain;

/**
 * Trạng thái xử lý của một ý kiến.
 * Giá trị chuỗi bám sát ràng buộc CK_Y_KIEN_TRANG_THAI trong CSDL
 * để tầng miền và tầng lưu trữ nói cùng một ngôn ngữ.
 */
public enum TrangThaiYKien {
    MOI_TIEP_NHAN("Mới tiếp nhận"),
    CHO_PHAN_NHOM("Chờ phân nhóm"),
    CHO_RA_SOAT("Chờ rà soát"),
    DA_RA_SOAT("Đã rà soát"),
    DA_PHAN_HOI("Đã phản hồi");

    private final String giaTri;

    TrangThaiYKien(String giaTri) {
        this.giaTri = giaTri;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public static TrangThaiYKien tuGiaTri(String giaTri) {
        if (giaTri != null) {
            for (TrangThaiYKien trangThai : values()) {
                if (trangThai.giaTri.equals(giaTri)) {
                    return trangThai;
                }
            }
        }
        throw new ViPhamNghiepVuException("Trạng thái ý kiến không hợp lệ: " + giaTri);
    }
}
