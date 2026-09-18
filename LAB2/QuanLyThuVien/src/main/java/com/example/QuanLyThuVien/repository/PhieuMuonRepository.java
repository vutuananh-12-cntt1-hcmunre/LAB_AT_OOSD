package com.example.quanlythuvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.PhieuMuon;

@Repository
public interface PhieuMuonRepository
        extends JpaRepository<PhieuMuon, String> {

    List<PhieuMuon>
    findByDocGia_MaDocGia(String maDocGia);
}