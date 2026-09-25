package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.KhuVuc;
import com.example.quanlykhachsan.repository.KhuVucRepository;

@Service
public class KhuVucService {

    private final KhuVucRepository khuVucRepository;

    public KhuVucService(KhuVucRepository khuVucRepository) {
        this.khuVucRepository = khuVucRepository;
    }

    public List<KhuVuc> getAll() {
        return khuVucRepository.findAll();
    }

    public KhuVuc save(KhuVuc khuVuc) {
        return khuVucRepository.save(khuVuc);
    }
}