package com.example.quanlythuvien.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlythuvien.entity.ChiTietPhieuMuon;
import com.example.quanlythuvien.entity.DauSach;
import com.example.quanlythuvien.entity.DocGia;
import com.example.quanlythuvien.entity.NhanVien;
import com.example.quanlythuvien.entity.PhieuMuon;
import com.example.quanlythuvien.service.DauSachService;
import com.example.quanlythuvien.service.DocGiaService;
import com.example.quanlythuvien.service.MuonTraService;
import com.example.quanlythuvien.service.NhanVienService;

@Controller
@RequestMapping("/muontra")
public class MuonTraController {

    private final MuonTraService muonTraService;
    private final DocGiaService docGiaService;
    private final NhanVienService nhanVienService;
    private final DauSachService dauSachService;

    public MuonTraController(
            MuonTraService muonTraService,
            DocGiaService docGiaService,
            NhanVienService nhanVienService,
            DauSachService dauSachService) {

        this.muonTraService = muonTraService;
        this.docGiaService = docGiaService;
        this.nhanVienService = nhanVienService;
        this.dauSachService = dauSachService;
    }

    // ==========================================
    // DANH SÁCH PHIẾU MƯỢN
    // ==========================================

    @GetMapping
    public String danhSach(Model model) {

        model.addAttribute(
                "danhSachPhieuMuon",
                muonTraService.findAll()
        );

        return "muontra/list";
    }

    // ==========================================
    // FORM TẠO PHIẾU MƯỢN
    // ==========================================

    @GetMapping("/them")
    public String them(Model model) {

        List<DocGia> danhSachDocGia =
                docGiaService.findAll();

        List<NhanVien> danhSachNhanVien =
                nhanVienService.findAll();

        List<DauSach> danhSachDauSach =
                dauSachService.findSachConTrongKho();

        model.addAttribute(
                "danhSachDocGia",
                danhSachDocGia
        );

        model.addAttribute(
                "danhSachNhanVien",
                danhSachNhanVien
        );

        model.addAttribute(
                "danhSachDauSach",
                danhSachDauSach
        );

        model.addAttribute(
                "ngayMuon",
                LocalDate.now()
        );

        model.addAttribute(
                "ngayHenTra",
                LocalDate.now().plusDays(7)
        );

        return "muontra/form";
    }

    // ==========================================
    // LƯU PHIẾU MƯỢN
    // ==========================================

    @PostMapping("/luu")
    public String luu(
            @RequestParam("maPhieuMuon")
            String maPhieuMuon,

            @RequestParam("maDocGia")
            String maDocGia,

            @RequestParam("maNhanVien")
            String maNhanVien,

            @RequestParam("ngayMuon")
            LocalDate ngayMuon,

            @RequestParam("ngayHenTra")
            LocalDate ngayHenTra,

            @RequestParam(
                    value = "danhSachMaDauSach",
                    required = false
            )
            List<String> danhSachMaDauSach,

            Model model) {

        try {

            muonTraService.taoPhieuMuon(
                    maPhieuMuon,
                    maDocGia,
                    maNhanVien,
                    ngayMuon,
                    ngayHenTra,
                    danhSachMaDauSach
            );

            return "redirect:/muontra";

        } catch (Exception e) {

            // Nạp lại dữ liệu cho form
            model.addAttribute(
                    "danhSachDocGia",
                    docGiaService.findAll()
            );

            model.addAttribute(
                    "danhSachNhanVien",
                    nhanVienService.findAll()
            );

            model.addAttribute(
                    "danhSachDauSach",
                    dauSachService.findSachConTrongKho()
            );

            // Giữ lại dữ liệu người dùng đã nhập
            model.addAttribute(
                    "maPhieuMuon",
                    maPhieuMuon
            );

            model.addAttribute(
                    "maDocGia",
                    maDocGia
            );

            model.addAttribute(
                    "maNhanVien",
                    maNhanVien
            );

            model.addAttribute(
                    "ngayMuon",
                    ngayMuon
            );

            model.addAttribute(
                    "ngayHenTra",
                    ngayHenTra
            );

            model.addAttribute(
                    "danhSachMaDauSach",
                    danhSachMaDauSach
            );

            model.addAttribute(
                    "loi",
                    e.getMessage()
            );

            return "muontra/form";
        }
    }

    // ==========================================
    // XEM CHI TIẾT PHIẾU MƯỢN
    // ==========================================

    @GetMapping("/chitiet/{id}")
    public String chiTiet(
            @PathVariable("id")
            String id,
            Model model) {

        PhieuMuon phieuMuon =
                muonTraService.findAll()
                        .stream()
                        .filter(p ->
                                p.getMaPhieuMuon()
                                        .equals(id)
                        )
                        .findFirst()
                        .orElse(null);

        if (phieuMuon == null) {
            return "redirect:/muontra";
        }

        List<ChiTietPhieuMuon> danhSachChiTiet =
                muonTraService.findChiTiet(id);

        model.addAttribute(
                "phieuMuon",
                phieuMuon
        );

        model.addAttribute(
                "danhSachChiTiet",
                danhSachChiTiet
        );

        return "muontra/chitiet";
    }

    // ==========================================
    // FORM TRẢ SÁCH
    // ==========================================

    @GetMapping("/tra/{id}")
    public String tra(
            @PathVariable("id")
            String id,
            Model model) {

        ChiTietPhieuMuon chiTiet =
                muonTraService.findChiTietById(id);

        if (chiTiet == null) {
            return "redirect:/muontra";
        }

        model.addAttribute(
                "maChiTiet",
                chiTiet.getMaChiTiet()
        );

        model.addAttribute(
                "maPhieuMuon",
                chiTiet.getPhieuMuon()
                        .getMaPhieuMuon()
        );

        model.addAttribute(
                "tenSach",
                chiTiet.getDauSach()
                        .getTenSach()
        );

        model.addAttribute(
                "ngayTra",
                LocalDate.now()
        );

        model.addAttribute(
                "tienPhat",
                BigDecimal.ZERO
        );

        model.addAttribute(
                "ghiChu",
                ""
        );

        return "muontra/tra";
    }

    // ==========================================
    // LƯU TRẢ SÁCH
    // ==========================================

    @PostMapping("/luutra")
    public String luuTra(
            @RequestParam("maChiTiet")
            String maChiTiet,

            @RequestParam("ngayTra")
            LocalDate ngayTra,

            @RequestParam(
                    value = "tienPhat",
                    required = false
            )
            BigDecimal tienPhat,

            @RequestParam(
                    value = "ghiChu",
                    required = false
            )
            String ghiChu,

            Model model) {

        try {

            muonTraService.traSach(
                    maChiTiet,
                    ngayTra,
                    tienPhat,
                    ghiChu
            );

            return "redirect:/muontra";

        } catch (Exception e) {

            ChiTietPhieuMuon chiTiet =
                    muonTraService
                            .findChiTietById(maChiTiet);

            model.addAttribute(
                    "maChiTiet",
                    maChiTiet
            );

            if (chiTiet != null) {

                model.addAttribute(
                        "maPhieuMuon",
                        chiTiet.getPhieuMuon()
                                .getMaPhieuMuon()
                );

                model.addAttribute(
                        "tenSach",
                        chiTiet.getDauSach()
                                .getTenSach()
                );
            }

            model.addAttribute(
                    "ngayTra",
                    ngayTra
            );

            model.addAttribute(
                    "tienPhat",
                    tienPhat
            );

            model.addAttribute(
                    "ghiChu",
                    ghiChu
            );

            model.addAttribute(
                    "loi",
                    e.getMessage()
            );

            return "muontra/tra";
        }
    }
}