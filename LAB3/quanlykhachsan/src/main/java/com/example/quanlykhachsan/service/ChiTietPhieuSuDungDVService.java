package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.ChiTietPhieuSuDungDV;
import com.example.quanlykhachsan.repository.ChiTietPhieuSuDungDVRepository;

@Service
public class ChiTietPhieuSuDungDVService {

    private final ChiTietPhieuSuDungDVRepository repository;

    public ChiTietPhieuSuDungDVService(
            ChiTietPhieuSuDungDVRepository repository) {

        this.repository = repository;
    }

    public List<ChiTietPhieuSuDungDV> getAll() {
        return repository.findAll();
    }

    public void save(ChiTietPhieuSuDungDV chiTiet) {
        repository.save(chiTiet);
    }
}