package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.ThanhToan;
import com.example.quanlykhachsan.repository.ThanhToanRepository;

@Service
public class ThanhToanService {

    private final ThanhToanRepository repository;

    public ThanhToanService(ThanhToanRepository repository) {
        this.repository = repository;
    }

    public List<ThanhToan> getAll() {
        return repository.findAll();
    }

    public void save(ThanhToan thanhToan) {
        repository.save(thanhToan);
    }
}