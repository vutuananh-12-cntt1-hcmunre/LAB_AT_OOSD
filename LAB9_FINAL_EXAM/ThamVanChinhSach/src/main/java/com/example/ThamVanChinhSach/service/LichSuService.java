package com.example.ThamVanChinhSach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.LichSuXuLyResponse;
import com.example.ThamVanChinhSach.repository.LichSuRepository;

@Service
public class LichSuService {
    private final LichSuRepository lichSuRepository;

    public LichSuService(LichSuRepository lichSuRepository) {
        this.lichSuRepository = lichSuRepository;
    }

    public void ghiLichSu(Integer maYKien, Integer maDuThao, Integer maNguoiThucHien, String hanhDong, String ghiChu) {
        lichSuRepository.ghiLichSu(maYKien, maDuThao, maNguoiThucHien, hanhDong, ghiChu);
    }

    public List<LichSuXuLyResponse> layTheoYKien(int maYKien) {
        return lichSuRepository.layTheoYKien(maYKien);
    }

    public List<LichSuXuLyResponse> layTheoDuThao(int maDuThao) {
        return lichSuRepository.layTheoDuThao(maDuThao);
    }
}
