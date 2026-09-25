package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.ChiTietDatPhong;
import com.example.quanlykhachsan.repository.ChiTietDatPhongRepository;

@Service
public class ChiTietDatPhongService {

    private final ChiTietDatPhongRepository repository;

    public ChiTietDatPhongService(ChiTietDatPhongRepository repository) {
        this.repository = repository;
    }

    public List<ChiTietDatPhong> getAll() {
        return repository.findAll();
    }

    public ChiTietDatPhong save(ChiTietDatPhong chiTietDatPhong) {
        return repository.save(chiTietDatPhong);
    }
}