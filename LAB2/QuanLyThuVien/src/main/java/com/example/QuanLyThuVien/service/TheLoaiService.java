package com.example.quanlythuvien.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlythuvien.entity.TheLoai;
import com.example.quanlythuvien.repository.TheLoaiRepository;

@Service
public class TheLoaiService {

    private final TheLoaiRepository theLoaiRepository;

    public TheLoaiService(TheLoaiRepository theLoaiRepository) {
        this.theLoaiRepository = theLoaiRepository;
    }

    public List<TheLoai> findAll() {
        return theLoaiRepository.findAll();
    }

    public Optional<TheLoai> findById(String maTheLoai) {
        return theLoaiRepository.findById(maTheLoai);
    }

    public TheLoai save(TheLoai theLoai) {
        return theLoaiRepository.save(theLoai);
    }

    public void deleteById(String maTheLoai) {
        theLoaiRepository.deleteById(maTheLoai);
    }

    public boolean existsById(String maTheLoai) {
        return theLoaiRepository.existsById(maTheLoai);
    }
}