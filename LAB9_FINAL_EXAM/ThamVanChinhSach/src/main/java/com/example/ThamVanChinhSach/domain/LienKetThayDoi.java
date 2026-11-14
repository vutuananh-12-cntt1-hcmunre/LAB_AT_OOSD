package com.example.ThamVanChinhSach.domain;

/**
 * Liên kết truy vết giữa một phiên bản dự thảo mới và ý kiến/phản hồi làm căn cứ.
 *
 * Đây là mắt xích cốt lõi của bài toán "tham vấn chính sách có truy vết phản hồi":
 * mỗi thay đổi của dự thảo phải chỉ ra được ý kiến nào đã dẫn tới thay đổi đó.
 */
public class LienKetThayDoi {

    private final int maDuThaoMoi;
    private final int maYKien;
    private final Integer maPhanHoi;
    private final String noiDungThayDoi;

    private LienKetThayDoi(int maDuThaoMoi, int maYKien, Integer maPhanHoi, String noiDungThayDoi) {
        this.maDuThaoMoi = maDuThaoMoi;
        this.maYKien = maYKien;
        this.maPhanHoi = maPhanHoi;
        this.noiDungThayDoi = noiDungThayDoi;
    }

    public static LienKetThayDoi taoMoi(int maDuThaoMoi,
                                        int maYKien,
                                        Integer maPhanHoi,
                                        String noiDungThayDoi) {
        if (maDuThaoMoi <= 0) {
            throw new ViPhamNghiepVuException("Liên kết phải trỏ tới một dự thảo mới hợp lệ.");
        }
        if (maYKien <= 0) {
            throw new ViPhamNghiepVuException("Liên kết phải trỏ tới một ý kiến hợp lệ.");
        }
        if (maPhanHoi != null && maPhanHoi <= 0) {
            throw new ViPhamNghiepVuException("Phản hồi được liên kết không hợp lệ.");
        }
        if (noiDungThayDoi == null || noiDungThayDoi.trim().isEmpty()) {
            throw new ViPhamNghiepVuException(
                    "Liên kết thay đổi phải mô tả nội dung thay đổi để bảo đảm truy vết.");
        }
        return new LienKetThayDoi(maDuThaoMoi, maYKien, maPhanHoi, noiDungThayDoi.trim());
    }

    public int getMaDuThaoMoi() {
        return maDuThaoMoi;
    }

    public int getMaYKien() {
        return maYKien;
    }

    public Integer getMaPhanHoi() {
        return maPhanHoi;
    }

    public String getNoiDungThayDoi() {
        return noiDungThayDoi;
    }
}
