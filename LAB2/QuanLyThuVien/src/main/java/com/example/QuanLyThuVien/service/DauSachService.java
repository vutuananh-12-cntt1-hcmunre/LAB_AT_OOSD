package com.example.quanlythuvien.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlythuvien.entity.DauSach;
import com.example.quanlythuvien.repository.DauSachRepository;

@Service
public class DauSachService {

    private final DauSachRepository dauSachRepository;

    public DauSachService(DauSachRepository dauSachRepository) {
        this.dauSachRepository = dauSachRepository;
    }

    public List<DauSach> findAll() {
        return dauSachRepository.findAll();
    }

    public Optional<DauSach> findById(String maDauSach) {
        return dauSachRepository.findById(maDauSach);
    }

    public DauSach save(DauSach dauSach) {
        return dauSachRepository.save(dauSach);
    }

    public void deleteById(String maDauSach) {
        dauSachRepository.deleteById(maDauSach);
    }

    public boolean existsById(String maDauSach) {
        return dauSachRepository.existsById(maDauSach);
    }

    public List<DauSach> findSachConTrongKho() {
        return dauSachRepository.findBySoLuongHienCoGreaterThan(0);
    }
}