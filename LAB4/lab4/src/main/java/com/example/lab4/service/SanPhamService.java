package com.example.lab4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lab4.entity.SanPham;
import com.example.lab4.repository.SanPhamRepository;

@Service
public class SanPhamService {

    private final SanPhamRepository sanPhamRepository;

    public SanPhamService(SanPhamRepository sanPhamRepository) {
        this.sanPhamRepository = sanPhamRepository;
    }

    // Lấy tất cả sản phẩm
    public List<SanPham> getTatCa() {
        return sanPhamRepository.findAll();
    }

    // Lấy sản phẩm theo mã nhóm
    public List<SanPham> getTheoNhom(String maNhom) {
        return sanPhamRepository.findByMaNhom(maNhom);
    }

    // Lấy sản phẩm theo mã sản phẩm
    public Optional<SanPham> getByMaSP(String maSP) {
        return sanPhamRepository.findById(maSP);
    }
}