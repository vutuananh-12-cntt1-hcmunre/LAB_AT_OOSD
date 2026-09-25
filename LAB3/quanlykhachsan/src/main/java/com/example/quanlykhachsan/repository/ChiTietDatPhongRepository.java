package com.example.quanlykhachsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlykhachsan.entity.ChiTietDatPhong;
import com.example.quanlykhachsan.entity.ChiTietDatPhongId;

public interface ChiTietDatPhongRepository
        extends JpaRepository<ChiTietDatPhong, ChiTietDatPhongId> {
}