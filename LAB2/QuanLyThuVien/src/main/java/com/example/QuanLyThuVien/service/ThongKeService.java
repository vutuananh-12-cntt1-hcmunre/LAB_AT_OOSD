package com.example.quanlythuvien.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlythuvien.entity.ChiTietPhieuMuon;
import com.example.quanlythuvien.entity.PhieuMuon;
import com.example.quanlythuvien.repository.ChiTietPhieuMuonRepository;
import com.example.quanlythuvien.repository.PhieuMuonRepository;

@Service
public class ThongKeService {

    private final ChiTietPhieuMuonRepository chiTietRepository;
    private final PhieuMuonRepository phieuMuonRepository;

    public ThongKeService(
            ChiTietPhieuMuonRepository chiTietRepository,
            PhieuMuonRepository phieuMuonRepository) {

        this.chiTietRepository = chiTietRepository;
        this.phieuMuonRepository = phieuMuonRepository;
    }

    // ==========================================
    // LẤY TẤT CẢ CHI TIẾT MƯỢN
    // ==========================================

    public List<ChiTietPhieuMuon> findAllChiTiet() {

        return chiTietRepository.findAll();
    }

    // ==========================================
    // TỔNG SỐ PHIẾU MƯỢN
    // ==========================================

    public int tongSoPhieuMuon() {

        return phieuMuonRepository.findAll().size();
    }

    // ==========================================
    // TỔNG SỐ SÁCH ĐÃ MƯỢN
    // ==========================================

    public long tongSoSachDaMuon() {

        return chiTietRepository.findAll()
                .size();
    }

    // ==========================================
    // SỐ SÁCH ĐÃ TRẢ
    // ==========================================

    public long soSachDaTra() {

        return chiTietRepository.findAll()
                .stream()
                .filter(ct -> ct.getNgayTra() != null)
                .count();
    }

    // ==========================================
    // SỐ SÁCH ĐANG MƯỢN
    // ==========================================

    public long soSachDangMuon() {

        return chiTietRepository.findAll()
                .stream()
                .filter(ct -> ct.getNgayTra() == null)
                .count();
    }

    // ==========================================
    // SỐ SÁCH QUÁ HẠN
    // ==========================================

    public long soSachQuaHan() {

        LocalDate homNay = LocalDate.now();

        return chiTietRepository.findAll()
                .stream()
                .filter(ct -> ct.getNgayTra() == null)
                .filter(ct -> {

                    PhieuMuon phieuMuon =
                            ct.getPhieuMuon();

                    return phieuMuon != null
                            && phieuMuon.getNgayHenTra() != null
                            && phieuMuon.getNgayHenTra()
                                    .isBefore(homNay);
                })
                .count();
    }

    // ==========================================
    // SỐ SÁCH CÓ TIỀN PHẠT
    // ==========================================

    public long soSachBiPhat() {

        return chiTietRepository.findAll()
                .stream()
                .filter(ct ->
                        ct.getTienPhat() != null
                                &&
                        ct.getTienPhat()
                                .signum() > 0)
                .count();
    }

    // ==========================================
    // TỔNG TIỀN PHẠT
    // ==========================================

    public java.math.BigDecimal tongTienPhat() {

        return chiTietRepository.findAll()
                .stream()
                .map(ChiTietPhieuMuon::getTienPhat)
                .filter(tien -> tien != null)
                .reduce(
                        java.math.BigDecimal.ZERO,
                        java.math.BigDecimal::add
                );
    }
}