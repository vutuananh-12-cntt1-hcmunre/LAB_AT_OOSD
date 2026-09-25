package com.example.quanlykhachsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlykhachsan.entity.KhachHang;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {
}