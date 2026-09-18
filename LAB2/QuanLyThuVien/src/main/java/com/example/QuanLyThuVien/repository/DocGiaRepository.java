package com.example.quanlythuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.DocGia;

@Repository
public interface DocGiaRepository extends JpaRepository<DocGia, String> {
}