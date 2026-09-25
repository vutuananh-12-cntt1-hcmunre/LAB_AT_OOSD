package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.PhieuLapDat;
import com.example.quanlykhachsan.repository.PhieuLapDatRepository;

@Service
public class PhieuLapDatService {

    private final PhieuLapDatRepository repository;

    public PhieuLapDatService(PhieuLapDatRepository repository) {
        this.repository = repository;
    }

    public List<PhieuLapDat> getAll() {
        return repository.findAll();
    }

    public PhieuLapDat save(PhieuLapDat phieuLapDat) {
        return repository.save(phieuLapDat);
    }
}