package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.HoaDon;
import com.example.quanlykhachsan.repository.HoaDonRepository;

@Service
public class HoaDonService {

    private final HoaDonRepository repository;

    public HoaDonService(HoaDonRepository repository) {
        this.repository = repository;
    }

    public List<HoaDon> getAll() {
        return repository.findAll();
    }

    public void save(HoaDon hoaDon) {
        repository.save(hoaDon);
    }
}