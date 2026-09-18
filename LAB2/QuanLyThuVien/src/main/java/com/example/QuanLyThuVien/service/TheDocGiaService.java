package com.example.quanlythuvien.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlythuvien.entity.TheDocGia;
import com.example.quanlythuvien.repository.TheDocGiaRepository;

@Service
public class TheDocGiaService {

    private final TheDocGiaRepository theDocGiaRepository;

    public TheDocGiaService(TheDocGiaRepository theDocGiaRepository) {
        this.theDocGiaRepository = theDocGiaRepository;
    }

    public List<TheDocGia> findAll() {
        return theDocGiaRepository.findAll();
    }

    public Optional<TheDocGia> findById(String id) {
        return theDocGiaRepository.findById(id);
    }

    public TheDocGia save(TheDocGia theDocGia) {
        return theDocGiaRepository.save(theDocGia);
    }

    public void deleteById(String id) {
        theDocGiaRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return theDocGiaRepository.existsById(id);
    }

    // Kiểm tra độc giả đã có thẻ đang hoạt động chưa
    public boolean daCoTheDangHoatDong(String maDocGia) {
        return theDocGiaRepository
                .existsByDocGia_MaDocGiaAndTrangThaiTrue(maDocGia);
    }

    // Tìm thẻ đang hoạt động và còn hạn
    public Optional<TheDocGia> timTheHopLe(
            String maDocGia,
            LocalDate ngayHienTai) {

        return theDocGiaRepository
                .findByDocGia_MaDocGiaAndTrangThaiTrueAndHanSuDungGreaterThanEqual(
                        maDocGia,
                        ngayHienTai
                );
    }
}