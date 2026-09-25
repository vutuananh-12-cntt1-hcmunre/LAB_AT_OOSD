package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.TienNghi;
import com.example.quanlykhachsan.repository.TienNghiRepository;

@Service
public class TienNghiService {

    private final TienNghiRepository repository;

    public TienNghiService(TienNghiRepository repository) {
        this.repository = repository;
    }

    public List<TienNghi> getAll() {
        return repository.findAll();
    }

    public TienNghi save(TienNghi tienNghi) {
        return repository.save(tienNghi);
    }
}