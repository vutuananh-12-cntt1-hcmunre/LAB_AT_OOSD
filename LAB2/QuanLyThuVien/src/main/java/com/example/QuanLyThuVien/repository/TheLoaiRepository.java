package com.example.quanlythuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.TheLoai;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai, String> {
}