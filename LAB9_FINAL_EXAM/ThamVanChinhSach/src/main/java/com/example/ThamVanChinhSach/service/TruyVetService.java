package com.example.ThamVanChinhSach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.DuThaoTiepThuResponse;
import com.example.ThamVanChinhSach.dto.LienKetTruyVetResponse;
import com.example.ThamVanChinhSach.repository.TruyVetRepository;

/** Ứng dụng: cung cấp dữ liệu truy vết hai chiều cho tầng giao diện. */
@Service
public class TruyVetService {
    private final TruyVetRepository truyVetRepository;

    public TruyVetService(TruyVetRepository truyVetRepository) {
        this.truyVetRepository = truyVetRepository;
    }

    public List<LienKetTruyVetResponse> layYKienCuaDuThao(int maDuThao) {
        return truyVetRepository.layYKienCuaDuThao(maDuThao);
    }

    public List<DuThaoTiepThuResponse> layDuThaoTiepThuYKien(int maYKien) {
        return truyVetRepository.layDuThaoTiepThuYKien(maYKien);
    }

    /** Chọn phản hồi phù hợp để gắn vào liên kết thay đổi (null nếu chưa phản hồi). */
    public Integer timMaPhanHoiGanNhat(int maYKien, String ketQuaXuLy) {
        return truyVetRepository.timMaPhanHoiGanNhat(maYKien, ketQuaXuLy);
    }
}
