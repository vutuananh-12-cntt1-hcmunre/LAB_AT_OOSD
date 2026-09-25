package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.QuyDinhDenBu;
import com.example.quanlykhachsan.repository.QuyDinhDenBuRepository;

@Service
public class QuyDinhDenBuService {

    private final QuyDinhDenBuRepository repository;

    public QuyDinhDenBuService(QuyDinhDenBuRepository repository) {
        this.repository = repository;
    }

    public List<QuyDinhDenBu> getAll() {
        return repository.findAll();
    }

    public void save(QuyDinhDenBu quyDinhDenBu) {
        repository.save(quyDinhDenBu);
    }
}