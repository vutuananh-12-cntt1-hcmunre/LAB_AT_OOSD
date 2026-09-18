package com.example.quanlythuvien.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlythuvien.entity.NhanVien;
import com.example.quanlythuvien.repository.NhanVienRepository;

@Service
public class NhanVienService {

    private final NhanVienRepository nhanVienRepository;

    public NhanVienService(NhanVienRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    // Lấy toàn bộ nhân viên
    public List<NhanVien> findAll() {
        return nhanVienRepository.findAll();
    }

    // Tìm nhân viên theo mã
    public Optional<NhanVien> findById(String maNhanVien) {
        return nhanVienRepository.findById(maNhanVien);
    }

    // Thêm hoặc cập nhật nhân viên
    public NhanVien save(NhanVien nhanVien) {
        return nhanVienRepository.save(nhanVien);
    }

    // Xóa nhân viên
    public void deleteById(String maNhanVien) {
        nhanVienRepository.deleteById(maNhanVien);
    }

    // Kiểm tra mã nhân viên đã tồn tại
    public boolean existsById(String maNhanVien) {
        return nhanVienRepository.existsById(maNhanVien);
    }
}