package com.example.quanlykhachsan.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.HoaDon;
import com.example.quanlykhachsan.service.HoaDonService;
import com.example.quanlykhachsan.service.NhanVienService;
import com.example.quanlykhachsan.service.PhieuDatPhongService;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {

    private final HoaDonService hoaDonService;
    private final PhieuDatPhongService phieuDatPhongService;
    private final NhanVienService nhanVienService;

    public HoaDonController(
            HoaDonService hoaDonService,
            PhieuDatPhongService phieuDatPhongService,
            NhanVienService nhanVienService) {

        this.hoaDonService = hoaDonService;
        this.phieuDatPhongService = phieuDatPhongService;
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsHoaDon",
                hoaDonService.getAll()
        );

        model.addAttribute(
                "dsPhieuDatPhong",
                phieuDatPhongService.getAll()
        );

        model.addAttribute(
                "dsNhanVien",
                nhanVienService.getAll()
        );

        HoaDon hoaDon = new HoaDon();

        hoaDon.setNgayLap(LocalDateTime.now());
        hoaDon.setSoNgayTinhTien(1);
        hoaDon.setTienPhong(BigDecimal.ZERO);
        hoaDon.setTienDichVu(BigDecimal.ZERO);
        hoaDon.setTrangThai("Chưa thanh toán");

        model.addAttribute("hoaDon", hoaDon);

        return "hoadon";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("hoaDon") HoaDon hoaDon) {

        if (hoaDon.getNgayLap() == null) {
            hoaDon.setNgayLap(LocalDateTime.now());
        }

        if (hoaDon.getSoNgayTinhTien() == null ||
                hoaDon.getSoNgayTinhTien() <= 0) {
            hoaDon.setSoNgayTinhTien(1);
        }

        if (hoaDon.getTienPhong() == null) {
            hoaDon.setTienPhong(BigDecimal.ZERO);
        }

        if (hoaDon.getTienDichVu() == null) {
            hoaDon.setTienDichVu(BigDecimal.ZERO);
        }

        if (hoaDon.getTrangThai() == null ||
                hoaDon.getTrangThai().isBlank()) {
            hoaDon.setTrangThai("Chưa thanh toán");
        }

        hoaDonService.save(hoaDon);

        return "redirect:/hoa-don";
    }
}