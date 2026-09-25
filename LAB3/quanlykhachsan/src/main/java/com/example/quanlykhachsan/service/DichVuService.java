package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.DichVu;
import com.example.quanlykhachsan.repository.DichVuRepository;

@Service
public class DichVuService {

    private final DichVuRepository dichVuRepository;

    public DichVuService(DichVuRepository dichVuRepository) {
        this.dichVuRepository = dichVuRepository;
    }

    public List<DichVu> getAll() {
        return dichVuRepository.findAll();
    }

    public void save(DichVu dichVu) {
        dichVuRepository.save(dichVu);
    }
}