package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.NguoiLuuTru;
import com.example.quanlykhachsan.repository.NguoiLuuTruRepository;

@Service
public class NguoiLuuTruService {

    private final NguoiLuuTruRepository repository;

    public NguoiLuuTruService(NguoiLuuTruRepository repository) {
        this.repository = repository;
    }

    public List<NguoiLuuTru> getAll() {
        return repository.findAll();
    }

    public NguoiLuuTru save(NguoiLuuTru nguoiLuuTru) {
        return repository.save(nguoiLuuTru);
    }
}