package com.example.ThamVanChinhSach.domain;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Ý kiến tham vấn.
 *
 * Đây là đối tượng miền có hành vi, không phải túi dữ liệu:
 * - Ý kiến không bao giờ bị xóa hay bị loại bỏ (bảo đảm truy vết).
 * - Nhóm do bộ gợi ý đề xuất chỉ được ghi vào MaNhom_AI_DeXuat.
 * - Nhóm chính thức chỉ được chốt khi có người rà soát và thời điểm rà soát.
 */
public class YKien {

    private static final Set<String> KET_QUA_PHAN_HOI_HOP_LE = Set.of(
            "Tiếp thu", "Tiếp thu một phần", "Không tiếp thu");

    private final int maYKien;
    private final int maChuDe;
    private final int nguoiGui;
    private final String noiDung;
    private final LocalDateTime thoiDiemGui;

    private TrangThaiYKien trangThai;
    private Integer maNhomAI;
    private Integer maNhomChinhThuc;
    private Integer nguoiRaSoat;
    private LocalDateTime thoiDiemRaSoat;

    public YKien(int maYKien,
                 int maChuDe,
                 int nguoiGui,
                 String noiDung,
                 LocalDateTime thoiDiemGui,
                 String trangThai,
                 Integer maNhomAI,
                 Integer maNhomChinhThuc,
                 Integer nguoiRaSoat,
                 LocalDateTime thoiDiemRaSoat) {
        if (maChuDe <= 0) {
            throw new ViPhamNghiepVuException("Ý kiến phải thuộc một chủ đề tham vấn.");
        }
        if (nguoiGui <= 0) {
            throw new ViPhamNghiepVuException("Ý kiến phải có người gửi hợp lệ.");
        }
        if (noiDung == null || noiDung.trim().isEmpty()) {
            throw new ViPhamNghiepVuException("Nội dung ý kiến không được để trống.");
        }
        this.maYKien = maYKien;
        this.maChuDe = maChuDe;
        this.nguoiGui = nguoiGui;
        this.noiDung = noiDung;
        this.thoiDiemGui = thoiDiemGui;
        this.trangThai = TrangThaiYKien.tuGiaTri(trangThai);
        this.maNhomAI = maNhomAI;
        this.maNhomChinhThuc = maNhomChinhThuc;
        this.nguoiRaSoat = nguoiRaSoat;
        this.thoiDiemRaSoat = thoiDiemRaSoat;
    }

    /**
     * Ghi nhận nhóm do bộ gợi ý tự động đề xuất.
     * Không được chạm vào nhóm chính thức: quyết định cuối cùng thuộc về con người.
     */
    public void ghiNhanDeXuatNhom(int maNhomDeXuat) {
        if (maNhomDeXuat <= 0) {
            throw new ViPhamNghiepVuException("Nhóm đề xuất không hợp lệ.");
        }
        if (maNhomChinhThuc != null) {
            throw new ViPhamNghiepVuException(
                    "Ý kiến đã được chốt nhóm chính thức, không được ghi đè bằng đề xuất tự động.");
        }
        this.maNhomAI = maNhomDeXuat;
        this.trangThai = TrangThaiYKien.CHO_RA_SOAT;
    }

    /**
     * Chốt nhóm chính thức. Bắt buộc có người rà soát và thời điểm rà soát
     * (tương ứng ràng buộc CK_Y_KIEN_NHOM_DA_RA_SOAT và trigger bảo vệ nhóm chính thức).
     */
    public void xacNhanRaSoat(int maNhomChinhThuc, int maNguoiRaSoat, LocalDateTime thoiDiemRaSoat) {
        if (maNhomChinhThuc <= 0) {
            throw new ViPhamNghiepVuException("Nhóm chính thức không hợp lệ.");
        }
        if (maNguoiRaSoat <= 0) {
            throw new ViPhamNghiepVuException(
                    "Phân nhóm chính thức bắt buộc phải do người rà soát xác nhận.");
        }
        if (thoiDiemRaSoat == null) {
            throw new ViPhamNghiepVuException("Thời điểm rà soát không được để trống.");
        }
        this.maNhomChinhThuc = maNhomChinhThuc;
        this.nguoiRaSoat = maNguoiRaSoat;
        this.thoiDiemRaSoat = thoiDiemRaSoat;
        this.trangThai = TrangThaiYKien.DA_RA_SOAT;
    }

    /**
     * Kiểm tra dữ liệu phản hồi trước khi ghi. Việc phản hồi không làm mất ý kiến;
     * ý kiến chỉ chuyển trạng thái và giữ nguyên nội dung gốc.
     */
    public void kiemTraPhanHoi(String ketQuaXuLy, String noiDungGiaiThich) {
        if (ketQuaXuLy == null || !KET_QUA_PHAN_HOI_HOP_LE.contains(ketQuaXuLy.trim())) {
            throw new ViPhamNghiepVuException(
                    "Kết quả xử lý chỉ nhận: Tiếp thu, Tiếp thu một phần hoặc Không tiếp thu.");
        }
        if (noiDungGiaiThich == null || noiDungGiaiThich.trim().isEmpty()) {
            throw new ViPhamNghiepVuException("Nội dung giải thích phản hồi không được để trống.");
        }
    }

    public boolean daRaSoat() {
        return maNhomChinhThuc != null && nguoiRaSoat != null && thoiDiemRaSoat != null;
    }

    public boolean daPhanHoi() {
        return trangThai == TrangThaiYKien.DA_PHAN_HOI;
    }

    public int getMaYKien() {
        return maYKien;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public int getNguoiGui() {
        return nguoiGui;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public LocalDateTime getThoiDiemGui() {
        return thoiDiemGui;
    }

    public TrangThaiYKien getTrangThai() {
        return trangThai;
    }

    public String getTrangThaiChuoi() {
        return trangThai.getGiaTri();
    }

    public Integer getMaNhomAI() {
        return maNhomAI;
    }

    public Integer getMaNhomChinhThuc() {
        return maNhomChinhThuc;
    }

    public Integer getNguoiRaSoat() {
        return nguoiRaSoat;
    }

    public LocalDateTime getThoiDiemRaSoat() {
        return thoiDiemRaSoat;
    }
}
