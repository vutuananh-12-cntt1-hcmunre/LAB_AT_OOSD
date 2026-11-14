package com.example.ThamVanChinhSach.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.PhanHoiNhomRequest;
import com.example.ThamVanChinhSach.repository.PhanHoiRepository;

@Service
public class PhanHoiService {
    private final PhanHoiRepository phanHoiRepository;

    private static final List<String> KET_QUA_HOP_LE = Arrays.asList(
            "Tiếp thu", "Tiếp thu một phần", "Không tiếp thu"
    );

    public PhanHoiService(PhanHoiRepository phanHoiRepository) {
        this.phanHoiRepository = phanHoiRepository;
    }

    public String phanHoiTheoNhom(PhanHoiNhomRequest request, int nguoiPhanHoi) {
        try {
            if (request.getKetQuaXuLy() == null || !KET_QUA_HOP_LE.contains(request.getKetQuaXuLy())) {
                return "Lỗi: Kết quả xử lý không hợp lệ! Chỉ chấp nhận: Tiếp thu, Tiếp thu một phần, Không tiếp thu";
            }
            if (request.getNoiDungGiaiThich() == null || request.getNoiDungGiaiThich().trim().isEmpty()) {
                return "Lỗi: Nội dung giải thích không được để trống!";
            }
            phanHoiRepository.phanHoiTheoNhom(request.getMaNhom(), request.getKetQuaXuLy(),
                    request.getNoiDungGiaiThich(), nguoiPhanHoi);
            return "Phản hồi theo nhóm thành công!";
        } catch (Exception e) {
            return "Lỗi phản hồi theo nhóm: " + e.getMessage();
        }
    }
}
