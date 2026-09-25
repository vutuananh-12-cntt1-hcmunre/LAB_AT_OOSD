package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.Phong;
import com.example.quanlykhachsan.repository.PhongRepository;

@Service
public class PhongService {

    private final PhongRepository phongRepository;

    public PhongService(PhongRepository phongRepository) {
        this.phongRepository = phongRepository;
    }

    public List<Phong> getAllPhong() {
        return phongRepository.findAll();
    }

    public Phong save(Phong phong) {
        return phongRepository.save(phong);
    }
}