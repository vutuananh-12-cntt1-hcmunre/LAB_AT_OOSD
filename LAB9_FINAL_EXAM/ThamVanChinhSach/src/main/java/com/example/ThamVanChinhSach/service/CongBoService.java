package com.example.ThamVanChinhSach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.CongBoKetQuaRequest;
import com.example.ThamVanChinhSach.dto.CongBoKetQuaResponse;
import com.example.ThamVanChinhSach.repository.CongBoRepository;

@Service
public class CongBoService {
    private final CongBoRepository congBoRepository;

    public CongBoService(CongBoRepository congBoRepository) {
        this.congBoRepository = congBoRepository;
    }

    public String taoCongBo(CongBoKetQuaRequest request, int nguoiCongBo) {
        try {
            if (request.getNoiDungCongBo() == null || request.getNoiDungCongBo().trim().isEmpty()) {
                return "Lỗi: Nội dung công bố không được để trống!";
            }
            Integer maCongBo = congBoRepository.taoCongBo(request, nguoiCongBo);
            return "Công bố kết quả thành công. Mã công bố: " + maCongBo;
        } catch (Exception e) {
            return "Lỗi công bố kết quả: " + e.getMessage();
        }
    }

    public List<CongBoKetQuaResponse> layDanhSachTheoChuDe(int maChuDe) {
        return congBoRepository.layDanhSachTheoChuDe(maChuDe);
    }

    public List<CongBoKetQuaResponse> layTatCa() {
        return congBoRepository.layTatCa();
    }
}
