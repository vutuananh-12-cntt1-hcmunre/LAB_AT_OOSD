package com.example.quanlythuvien.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.TheDocGia;

@Repository
public interface TheDocGiaRepository extends JpaRepository<TheDocGia, String> {

    Optional<TheDocGia> findByDocGia_MaDocGiaAndTrangThaiTrue(
            String maDocGia
    );

    Optional<TheDocGia> findByDocGia_MaDocGiaAndTrangThaiTrueAndHanSuDungGreaterThanEqual(
            String maDocGia,
            LocalDate ngayHienTai
    );

    boolean existsByDocGia_MaDocGiaAndTrangThaiTrue(
            String maDocGia
    );
}