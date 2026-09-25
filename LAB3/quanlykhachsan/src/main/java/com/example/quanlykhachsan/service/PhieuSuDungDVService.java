package com.example.quanlykhachsan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlykhachsan.entity.PhieuSuDungDV;
import com.example.quanlykhachsan.repository.PhieuSuDungDVRepository;

@Service
public class PhieuSuDungDVService {

    private final PhieuSuDungDVRepository phieuSuDungDVRepository;

    public PhieuSuDungDVService(
            PhieuSuDungDVRepository phieuSuDungDVRepository) {

        this.phieuSuDungDVRepository = phieuSuDungDVRepository;
    }

    public List<PhieuSuDungDV> getAll() {
        return phieuSuDungDVRepository.findAll();
    }

    public void save(PhieuSuDungDV phieuSuDungDV) {
        phieuSuDungDVRepository.save(phieuSuDungDV);
    }
}