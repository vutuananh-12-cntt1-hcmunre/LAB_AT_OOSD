package com.example.quanlythuvien.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.NhaXuatBan;

@Repository
public interface NhaXuatBanRepository extends JpaRepository<NhaXuatBan, String> {
}