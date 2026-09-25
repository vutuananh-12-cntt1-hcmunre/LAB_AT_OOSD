package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.PhieuDenBu;
import com.example.quanlykhachsan.repository.PhieuDenBuRepository;

@Service
public class PhieuDenBuService {

    private final PhieuDenBuRepository repository;

    public PhieuDenBuService(PhieuDenBuRepository repository) {
        this.repository = repository;
    }

    public List<PhieuDenBu> getAll() {
        return repository.findAll();
    }

    public void save(PhieuDenBu phieuDenBu) {
        repository.save(phieuDenBu);
    }
}