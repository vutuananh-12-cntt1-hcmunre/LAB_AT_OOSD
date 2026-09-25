package com.example.quanlykhachsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlykhachsan.entity.ChiTietPhieuSuDungDV;
import com.example.quanlykhachsan.entity.ChiTietPhieuSuDungDVId;

public interface ChiTietPhieuSuDungDVRepository
        extends JpaRepository<
                ChiTietPhieuSuDungDV,
                ChiTietPhieuSuDungDVId> {
}