package com.example.ThamVanChinhSach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.domain.DuThao;
import com.example.ThamVanChinhSach.domain.LienKetThayDoi;
import com.example.ThamVanChinhSach.domain.ViPhamNghiepVuException;
import com.example.ThamVanChinhSach.dto.DuThaoRequest;
import com.example.ThamVanChinhSach.dto.DuThaoResponse;
import com.example.ThamVanChinhSach.dto.LienKetThayDoiRequest;
import com.example.ThamVanChinhSach.repository.DuThaoRepository;

@Service
public class DuThaoService {
    private final DuThaoRepository duThaoRepository;

    public DuThaoService(DuThaoRepository duThaoRepository) {
        this.duThaoRepository = duThaoRepository;
    }

    public String taoDuThao(DuThaoRequest request, int nguoiCapNhat) {
        try {
            // Kiểm tra bằng đối tượng miền: tên, phiên bản, nội dung, chủ đề, người cập nhật.
            DuThao.taoMoi(request.getMaChuDe(),
                    request.getTenDuThao(),
                    request.getPhienBan(),
                    request.getNoiDung(),
                    nguoiCapNhat,
                    request.getMaDuThaoTruoc(),
                    request.getGhiChuThayDoi());

            Integer maMoi = duThaoRepository.taoDuThao(request, nguoiCapNhat);
            return "Tạo dự thảo thành công. Mã dự thảo: " + maMoi;
        } catch (ViPhamNghiepVuException e) {
            return "Lỗi: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi tạo dự thảo: " + e.getMessage();
        }
    }

    public List<DuThaoResponse> layDanhSachTheoChuDe(int maChuDe) {
        return duThaoRepository.layDanhSachTheoChuDe(maChuDe);
    }

    public DuThaoResponse layChiTiet(int maDuThao) {
        return duThaoRepository.layChiTiet(maDuThao);
    }

    public String congBoDuThao(int maDuThao) {
        try {
            duThaoRepository.congBoDuThao(maDuThao);
            return "Công bố dự thảo thành công!";
        } catch (Exception e) {
            return "Lỗi công bố dự thảo: " + e.getMessage();
        }
    }

    public String taoLienKetThayDoi(LienKetThayDoiRequest request) {
        try {
            LienKetThayDoi lienKet = LienKetThayDoi.taoMoi(
                    request.getMaDuThaoMoi(),
                    request.getMaYKien(),
                    request.getMaPhanHoi(),
                    request.getNoiDungThayDoi());
            duThaoRepository.taoLienKetThayDoi(lienKet);
            return "Liên kết ý kiến với dự thảo thành công!";
        } catch (ViPhamNghiepVuException e) {
            return "Lỗi liên kết thay đổi: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi liên kết thay đổi: " + e.getMessage();
        }
    }
}
