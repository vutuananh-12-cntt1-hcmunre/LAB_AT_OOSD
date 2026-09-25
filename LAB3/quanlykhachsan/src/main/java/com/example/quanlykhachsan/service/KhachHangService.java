package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.KhachHang;
import com.example.quanlykhachsan.repository.KhachHangRepository;

@Service
public class KhachHangService {

    private final KhachHangRepository repository;

    public KhachHangService(KhachHangRepository repository) {
        this.repository = repository;
    }

    public List<KhachHang> getAll() {
        return repository.findAll();
    }

    public KhachHang save(KhachHang khachHang) {
        return repository.save(khachHang);
    }
}