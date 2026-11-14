package com.example.ThamVanChinhSach.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.CapNhatNguoiDungRequest;
import com.example.ThamVanChinhSach.dto.NguoiDungResponse;
import com.example.ThamVanChinhSach.repository.NguoiDungRepository;

@Service
public class NguoiDungService {
    private final NguoiDungRepository nguoiDungRepository;

    private static final List<String> VAI_TRO_HOP_LE = Arrays.asList(
            "NGUOI_DAN", "RA_SOAT", "CAN_BO_CO_QUAN", "ADMIN"
    );

    public NguoiDungService(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    public List<NguoiDungResponse> layDanhSach() {
        return nguoiDungRepository.layDanhSach();
    }

    public NguoiDungResponse layChiTiet(int maNguoiDung) {
        return nguoiDungRepository.layChiTiet(maNguoiDung);
    }

    public String capNhatNguoiDung(int maNguoiDung, CapNhatNguoiDungRequest request) {
        try {
            if (request.getMaVaiTro() != null && !VAI_TRO_HOP_LE.contains(request.getMaVaiTro())) {
                return "Lỗi: Mã vai trò không hợp lệ!";
            }
            nguoiDungRepository.capNhatNguoiDung(maNguoiDung, request);
            return "Cập nhật người dùng thành công!";
        } catch (Exception e) {
            return "Lỗi cập nhật người dùng: " + e.getMessage();
        }
    }
}
