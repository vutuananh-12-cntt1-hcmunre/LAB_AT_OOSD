package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.PhieuDatPhong;
import com.example.quanlykhachsan.repository.PhieuDatPhongRepository;

@Service
public class PhieuDatPhongService {

    private final PhieuDatPhongRepository repository;

    public PhieuDatPhongService(PhieuDatPhongRepository repository) {
        this.repository = repository;
    }

    public List<PhieuDatPhong> getAll() {
        return repository.findAll();
    }

    public PhieuDatPhong save(PhieuDatPhong phieuDatPhong) {
        return repository.save(phieuDatPhong);
    }
}