package com.example.quanlykhachsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlykhachsan.entity.ChiTietPhieuDenBu;
import com.example.quanlykhachsan.entity.ChiTietPhieuDenBuId;

public interface ChiTietPhieuDenBuRepository
        extends JpaRepository<ChiTietPhieuDenBu, ChiTietPhieuDenBuId> {
}