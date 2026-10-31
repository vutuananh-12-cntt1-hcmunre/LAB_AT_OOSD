package com.example.lab4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab4.entity.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, String> {

    List<SanPham> findByMaNhom(String maNhom);
}