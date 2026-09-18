package com.example.quanlythuvien.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.DauSach;

import jakarta.persistence.LockModeType;

@Repository
public interface DauSachRepository extends JpaRepository<DauSach, String> {

    List<DauSach> findBySoLuongHienCoGreaterThan(int soLuong);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    Optional<DauSach> findById(String id);
}