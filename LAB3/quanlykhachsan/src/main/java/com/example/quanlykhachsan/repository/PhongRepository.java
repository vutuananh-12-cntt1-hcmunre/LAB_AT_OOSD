package com.example.quanlykhachsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlykhachsan.entity.Phong;

public interface PhongRepository extends JpaRepository<Phong, String> {
}