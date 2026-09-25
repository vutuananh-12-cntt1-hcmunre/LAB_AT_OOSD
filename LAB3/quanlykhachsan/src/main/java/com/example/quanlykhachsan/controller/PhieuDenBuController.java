package com.example.quanlykhachsan.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.PhieuDenBu;
import com.example.quanlykhachsan.service.ChiTietDatPhongService;
import com.example.quanlykhachsan.service.NhanVienService;
import com.example.quanlykhachsan.service.PhieuDenBuService;

@Controller
@RequestMapping("/phieu-den-bu")
public class PhieuDenBuController {

    private final PhieuDenBuService phieuDenBuService;
    private final ChiTietDatPhongService chiTietDatPhongService;
    private final NhanVienService nhanVienService;

    public PhieuDenBuController(
            PhieuDenBuService phieuDenBuService,
            ChiTietDatPhongService chiTietDatPhongService,
            NhanVienService nhanVienService) {

        this.phieuDenBuService = phieuDenBuService;
        this.chiTietDatPhongService = chiTietDatPhongService;
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsPhieuDenBu",
                phieuDenBuService.getAll()
        );

        model.addAttribute(
                "dsChiTietDatPhong",
                chiTietDatPhongService.getAll()
        );

        model.addAttribute(
                "dsNhanVien",
                nhanVienService.getAll()
        );

        PhieuDenBu phieu = new PhieuDenBu();

        phieu.setNgayLap(LocalDateTime.now());
        phieu.setTongTien(BigDecimal.ZERO);

        model.addAttribute("phieuDenBu", phieu);

        return "phieudenbu";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("phieuDenBu")
            PhieuDenBu phieuDenBu) {

        if (phieuDenBu.getNgayLap() == null) {
            phieuDenBu.setNgayLap(LocalDateTime.now());
        }

        if (phieuDenBu.getTongTien() == null) {
            phieuDenBu.setTongTien(BigDecimal.ZERO);
        }

        phieuDenBuService.save(phieuDenBu);

        return "redirect:/phieu-den-bu";
    }
}