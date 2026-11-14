package com.example.ThamVanChinhSach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.CoQuanRequest;
import com.example.ThamVanChinhSach.dto.CoQuanResponse;
import com.example.ThamVanChinhSach.repository.CoQuanRepository;

@Service
public class CoQuanService {
    private final CoQuanRepository coQuanRepository;

    public CoQuanService(CoQuanRepository coQuanRepository) {
        this.coQuanRepository = coQuanRepository;
    }

    public List<CoQuanResponse> layDanhSach() {
        return coQuanRepository.layDanhSach();
    }

    public CoQuanResponse layChiTiet(int maCoQuan) {
        return coQuanRepository.layChiTiet(maCoQuan);
    }

    public String taoCoQuan(CoQuanRequest request) {
        try {
            if (request.getTenCoQuan() == null || request.getTenCoQuan().trim().isEmpty()) {
                return "Lỗi: Tên cơ quan không được để trống!";
            }
            Integer maMoi = coQuanRepository.taoCoQuan(request);
            return "Tạo cơ quan thành công. Mã cơ quan: " + maMoi;
        } catch (Exception e) {
            return "Lỗi tạo cơ quan: " + e.getMessage();
        }
    }

    public String capNhatCoQuan(int maCoQuan, CoQuanRequest request) {
        try {
            if (request.getTenCoQuan() == null || request.getTenCoQuan().trim().isEmpty()) {
                return "Lỗi: Tên cơ quan không được để trống!";
            }
            coQuanRepository.capNhatCoQuan(maCoQuan, request);
            return "Cập nhật cơ quan thành công!";
        } catch (Exception e) {
            return "Lỗi cập nhật cơ quan: " + e.getMessage();
        }
    }

    public String capNhatTrangThai(int maCoQuan, boolean trangThai) {
        try {
            coQuanRepository.capNhatTrangThai(maCoQuan, trangThai);
            return trangThai ? "Đã kích hoạt cơ quan!" : "Đã vô hiệu hóa cơ quan!";
        } catch (Exception e) {
            return "Lỗi cập nhật trạng thái: " + e.getMessage();
        }
    }
}
