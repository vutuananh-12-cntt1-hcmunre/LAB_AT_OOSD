package com.example.quanlythuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.NhanVien;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, String> {
}