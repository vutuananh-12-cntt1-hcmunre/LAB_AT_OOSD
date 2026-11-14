package com.example.ThamVanChinhSach.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.CapNhatTrangThaiRequest;
import com.example.ThamVanChinhSach.dto.ChuDeRequest;
import com.example.ThamVanChinhSach.dto.ChuDeResponse;
import com.example.ThamVanChinhSach.dto.ChuDeUpdateRequest;
import com.example.ThamVanChinhSach.repository.ChuDeRepository;

@Service
public class ChuDeService {
    private final ChuDeRepository chuDeRepository;

    private static final List<String> TRANG_THAI_HOP_LE = Arrays.asList(
            "Bản nháp", "Đang mở", "Đã đóng", "Đã công bố"
    );

    public ChuDeService(ChuDeRepository chuDeRepository) {
        this.chuDeRepository = chuDeRepository;
    }

    public String taoChuDe(ChuDeRequest request, int nguoiTao) {
        try {
            if (request.getTieuDe() == null || request.getTieuDe().trim().isEmpty()) {
                return "Lỗi: Tiêu đề không được để trống!";
            }
            if (request.getThoiGianBatDau() == null || request.getThoiGianKetThuc() == null) {
                return "Lỗi: Vui lòng nhập đầy đủ thời gian bắt đầu và thời gian kết thúc!";
            }
            if (!request.getThoiGianKetThuc().isAfter(request.getThoiGianBatDau())) {
                return "Lỗi: Ngày giờ kết thúc phải sau ngày giờ bắt đầu!";
            }
            chuDeRepository.taoChuDe(request, nguoiTao);
            return "Tạo chủ đề tham vấn thành công!";
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "";
            if (msg.contains("CK_CHU_DE_THOI_GIAN")) {
                return "Lỗi: Ngày giờ kết thúc phải sau ngày giờ bắt đầu!";
            }
            return "Lỗi tạo chủ đề: " + msg;
        }
    }

    public List<ChuDeResponse> layDanhSachChuDe() {
        return chuDeRepository.layDanhSachChuDe();
    }

    public ChuDeResponse layChiTietChuDe(int maChuDe) {
        return chuDeRepository.layChiTietChuDe(maChuDe);
    }

    public String capNhatChuDe(int maChuDe, ChuDeUpdateRequest request) {
        try {
            if (request.getTieuDe() == null || request.getTieuDe().trim().isEmpty()) {
                return "Lỗi: Tiêu đề không được để trống!";
            }
            if (request.getThoiGianBatDau() != null && request.getThoiGianKetThuc() != null
                    && !request.getThoiGianKetThuc().isAfter(request.getThoiGianBatDau())) {
                return "Lỗi: Ngày giờ kết thúc phải sau ngày giờ bắt đầu!";
            }
            chuDeRepository.capNhatChuDe(maChuDe, request);
            return "Cập nhật chủ đề thành công!";
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "";
            if (msg.contains("CK_CHU_DE_THOI_GIAN")) {
                return "Lỗi: Ngày giờ kết thúc phải sau ngày giờ bắt đầu!";
            }
            return "Lỗi cập nhật chủ đề: " + msg;
        }
    }

    public String capNhatTrangThai(int maChuDe, CapNhatTrangThaiRequest request) {
        try {
            if (request.getTrangThai() == null || !TRANG_THAI_HOP_LE.contains(request.getTrangThai())) {
                return "Lỗi: Trạng thái không hợp lệ! Chỉ chấp nhận: Bản nháp, Đang mở, Đã đóng, Đã công bố";
            }
            chuDeRepository.capNhatTrangThai(maChuDe, request.getTrangThai());
            return "Cập nhật trạng thái chủ đề thành công!";
        } catch (Exception e) {
            return "Lỗi cập nhật trạng thái: " + e.getMessage();
        }
    }
}
