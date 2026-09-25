package com.example.quanlykhachsan.service;

import com.example.quanlykhachsan.entity.LoaiTienNghi;
import com.example.quanlykhachsan.repository.LoaiTienNghiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiTienNghiService {

    private final LoaiTienNghiRepository repository;

    public LoaiTienNghiService(LoaiTienNghiRepository repository) {
        this.repository = repository;
    }

    public List<LoaiTienNghi> getAll() {
        return repository.findAll();
    }

    public LoaiTienNghi save(LoaiTienNghi loaiTienNghi) {
        return repository.save(loaiTienNghi);
    }
}