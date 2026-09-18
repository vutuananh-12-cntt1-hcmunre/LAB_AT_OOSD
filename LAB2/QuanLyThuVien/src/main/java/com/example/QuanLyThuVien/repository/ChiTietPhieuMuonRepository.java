package com.example.quanlythuvien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlythuvien.entity.ChiTietPhieuMuon;

@Repository
public interface ChiTietPhieuMuonRepository
        extends JpaRepository<ChiTietPhieuMuon, String> {

    // Lấy danh sách chi tiết của một phiếu mượn
    List<ChiTietPhieuMuon>
    findByPhieuMuon_MaPhieuMuon(String maPhieuMuon);

    // Lấy những sách mà độc giả đang mượn và chưa trả
    List<ChiTietPhieuMuon>
    findByPhieuMuon_DocGia_MaDocGiaAndNgayTraIsNull(
            String maDocGia
    );

    // Kiểm tra độc giả có sách chưa trả hay không
    boolean existsByPhieuMuon_DocGia_MaDocGiaAndNgayTraIsNull(
            String maDocGia
    );
}