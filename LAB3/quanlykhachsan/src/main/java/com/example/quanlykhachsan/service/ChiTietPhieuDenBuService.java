package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.ChiTietPhieuDenBu;
import com.example.quanlykhachsan.repository.ChiTietPhieuDenBuRepository;

@Service
public class ChiTietPhieuDenBuService {

    private final ChiTietPhieuDenBuRepository repository;

    public ChiTietPhieuDenBuService(ChiTietPhieuDenBuRepository repository) {
        this.repository = repository;
    }

    public List<ChiTietPhieuDenBu> getAll() {
        return repository.findAll();
    }

    public void save(ChiTietPhieuDenBu chiTietPhieuDenBu) {
        repository.save(chiTietPhieuDenBu);
    }
}