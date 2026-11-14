package com.example.ThamVanChinhSach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.NhomYKienRequest;
import com.example.ThamVanChinhSach.dto.NhomYKienResponse;
import com.example.ThamVanChinhSach.repository.NhomYKienRepository;

@Service
public class NhomYKienService {
    private final NhomYKienRepository nhomYKienRepository;

    public NhomYKienService(NhomYKienRepository nhomYKienRepository) {
        this.nhomYKienRepository = nhomYKienRepository;
    }

    public List<NhomYKienResponse> layDanhSachTheoChuDe(int maChuDe) {
        return nhomYKienRepository.layDanhSachTheoChuDe(maChuDe);
    }

    public String taoNhom(NhomYKienRequest request) {
        try {
            if (request.getTenNhom() == null || request.getTenNhom().trim().isEmpty()) {
                return "Lỗi: Tên nhóm không được để trống!";
            }
            Integer maNhomMoi = nhomYKienRepository.taoNhom(request);
            return "Tạo nhóm ý kiến thành công. Mã nhóm: " + maNhomMoi;
        } catch (Exception e) {
            return "Lỗi tạo nhóm: " + e.getMessage();
        }
    }

    public String capNhatNhom(int maNhom, NhomYKienRequest request) {
        try {
            if (request.getTenNhom() == null || request.getTenNhom().trim().isEmpty()) {
                return "Lỗi: Tên nhóm không được để trống!";
            }
            nhomYKienRepository.capNhatNhom(maNhom, request.getTenNhom(), request.getMoTa());
            return "Cập nhật nhóm ý kiến thành công!";
        } catch (Exception e) {
            return "Lỗi cập nhật nhóm: " + e.getMessage();
        }
    }

    public String xoaNhom(int maNhom) {
        try {
            nhomYKienRepository.xoaNhom(maNhom);
            return "Xóa nhóm ý kiến thành công!";
        } catch (Exception e) {
            return "Lỗi xóa nhóm: " + e.getMessage();
        }
    }
}
