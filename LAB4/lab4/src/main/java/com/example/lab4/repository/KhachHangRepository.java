package com.example.lab4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab4.entity.KhachHang;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<KhachHang> findByUsername(String username);
}