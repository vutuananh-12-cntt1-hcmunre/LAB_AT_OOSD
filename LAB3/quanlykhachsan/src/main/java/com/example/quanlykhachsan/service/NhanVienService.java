package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.NhanVien;
import com.example.quanlykhachsan.repository.NhanVienRepository;

@Service
public class NhanVienService {

    private final NhanVienRepository repository;

    public NhanVienService(NhanVienRepository repository) {
        this.repository = repository;
    }

    public List<NhanVien> getAll() {
        return repository.findAll();
    }

    public NhanVien save(NhanVien nhanVien) {
        return repository.save(nhanVien);
    }
}