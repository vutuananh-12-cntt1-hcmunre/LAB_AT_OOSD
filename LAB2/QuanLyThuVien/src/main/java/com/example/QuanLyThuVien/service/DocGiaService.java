package com.example.quanlythuvien.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlythuvien.entity.DocGia;
import com.example.quanlythuvien.repository.DocGiaRepository;

@Service
public class DocGiaService {

    private final DocGiaRepository docGiaRepository;

    public DocGiaService(DocGiaRepository docGiaRepository) {
        this.docGiaRepository = docGiaRepository;
    }

    public List<DocGia> findAll() {
        return docGiaRepository.findAll();
    }

    public Optional<DocGia> findById(String id) {
        return docGiaRepository.findById(id);
    }

    public DocGia save(DocGia docGia) {
        return docGiaRepository.save(docGia);
    }

    public void deleteById(String id) {
        docGiaRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return docGiaRepository.existsById(id);
    }
}