package com.example.quanlykhachsan.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.PhieuDatPhong;
import com.example.quanlykhachsan.service.KhachHangService;
import com.example.quanlykhachsan.service.NhanVienService;
import com.example.quanlykhachsan.service.PhieuDatPhongService;

@Controller
@RequestMapping("/dat-phong")
public class PhieuDatPhongController {

    private final PhieuDatPhongService phieuDatPhongService;
    private final KhachHangService khachHangService;
    private final NhanVienService nhanVienService;

    public PhieuDatPhongController(
            PhieuDatPhongService phieuDatPhongService,
            KhachHangService khachHangService,
            NhanVienService nhanVienService) {

        this.phieuDatPhongService = phieuDatPhongService;
        this.khachHangService = khachHangService;
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsPhieuDatPhong",
                phieuDatPhongService.getAll()
        );

        model.addAttribute(
                "dsKhachHang",
                khachHangService.getAll()
        );

        model.addAttribute(
                "dsNhanVien",
                nhanVienService.getAll()
        );

        model.addAttribute(
                "phieuDatPhong",
                new PhieuDatPhong()
        );

        return "phieudatphong";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("phieuDatPhong")
            PhieuDatPhong phieuDatPhong) {

        if (phieuDatPhong.getNgayLap() == null) {
            phieuDatPhong.setNgayLap(LocalDateTime.now());
        }

        if (phieuDatPhong.getTienCoc() == null) {
            phieuDatPhong.setTienCoc(BigDecimal.ZERO);
        }

        if (phieuDatPhong.getTrangThai() == null
                || phieuDatPhong.getTrangThai().isBlank()) {

            phieuDatPhong.setTrangThai("Đã đặt");
        }

        phieuDatPhongService.save(phieuDatPhong);

        return "redirect:/dat-phong";
    }
}