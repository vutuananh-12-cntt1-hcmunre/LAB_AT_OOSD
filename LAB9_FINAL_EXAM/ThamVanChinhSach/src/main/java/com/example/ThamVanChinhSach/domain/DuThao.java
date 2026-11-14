package com.example.ThamVanChinhSach.domain;

/**
 * Dự thảo chính sách. Mỗi dự thảo là một phiên bản trong chuỗi phiên bản;
 * phiên bản mới có thể kế thừa phiên bản trước để bảo đảm truy vết thay đổi.
 */
public class DuThao {

    private static final String TRANG_THAI_BAN_NHAP = "Bản nháp";

    private final int maChuDe;
    private final String tenDuThao;
    private final String phienBan;
    private final String noiDung;
    private final int nguoiCapNhat;
    private final Integer maDuThaoTruoc;
    private final String ghiChuThayDoi;

    private DuThao(int maChuDe,
                   String tenDuThao,
                   String phienBan,
                   String noiDung,
                   int nguoiCapNhat,
                   Integer maDuThaoTruoc,
                   String ghiChuThayDoi) {
        this.maChuDe = maChuDe;
        this.tenDuThao = tenDuThao;
        this.phienBan = phienBan;
        this.noiDung = noiDung;
        this.nguoiCapNhat = nguoiCapNhat;
        this.maDuThaoTruoc = maDuThaoTruoc;
        this.ghiChuThayDoi = ghiChuThayDoi;
    }

    public static DuThao taoMoi(int maChuDe,
                                String tenDuThao,
                                String phienBan,
                                String noiDung,
                                int nguoiCapNhat,
                                Integer maDuThaoTruoc,
                                String ghiChuThayDoi) {
        if (maChuDe <= 0) {
            throw new ViPhamNghiepVuException("Dự thảo phải thuộc một chủ đề tham vấn.");
        }
        if (tenDuThao == null || tenDuThao.trim().isEmpty()) {
            throw new ViPhamNghiepVuException("Tên dự thảo không được để trống.");
        }
        if (phienBan == null || phienBan.trim().isEmpty()) {
            throw new ViPhamNghiepVuException("Phiên bản dự thảo không được để trống.");
        }
        if (noiDung == null || noiDung.trim().isEmpty()) {
            throw new ViPhamNghiepVuException("Nội dung dự thảo không được để trống.");
        }
        if (nguoiCapNhat <= 0) {
            throw new ViPhamNghiepVuException("Dự thảo phải có người cập nhật hợp lệ.");
        }
        if (maDuThaoTruoc != null && maDuThaoTruoc <= 0) {
            throw new ViPhamNghiepVuException("Dự thảo trước không hợp lệ.");
        }
        return new DuThao(maChuDe, tenDuThao.trim(), phienBan.trim(), noiDung,
                nguoiCapNhat, maDuThaoTruoc, ghiChuThayDoi);
    }

    public boolean laPhienBanKeTiep() {
        return maDuThaoTruoc != null;
    }

    public String getTrangThaiKhoiTao() {
        return TRANG_THAI_BAN_NHAP;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public String getTenDuThao() {
        return tenDuThao;
    }

    public String getPhienBan() {
        return phienBan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public int getNguoiCapNhat() {
        return nguoiCapNhat;
    }

    public Integer getMaDuThaoTruoc() {
        return maDuThaoTruoc;
    }

    public String getGhiChuThayDoi() {
        return ghiChuThayDoi;
    }
}
