package com.example.quanlykhachsan.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.ThanhToan;
import com.example.quanlykhachsan.service.HoaDonService;
import com.example.quanlykhachsan.service.ThanhToanService;

@Controller
@RequestMapping("/thanh-toan")
public class ThanhToanController {

    private final ThanhToanService thanhToanService;
    private final HoaDonService hoaDonService;

    public ThanhToanController(
            ThanhToanService thanhToanService,
            HoaDonService hoaDonService) {

        this.thanhToanService = thanhToanService;
        this.hoaDonService = hoaDonService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsThanhToan",
                thanhToanService.getAll()
        );

        model.addAttribute(
                "dsHoaDon",
                hoaDonService.getAll()
        );

        ThanhToan thanhToan = new ThanhToan();

        thanhToan.setNgayThanhToan(LocalDateTime.now());
        thanhToan.setSoTien(BigDecimal.ZERO);

        model.addAttribute(
                "thanhToan",
                thanhToan
        );

        return "thanhtoan";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("thanhToan")
            ThanhToan thanhToan) {

        if (thanhToan.getNgayThanhToan() == null) {
            thanhToan.setNgayThanhToan(LocalDateTime.now());
        }

        if (thanhToan.getSoTien() == null) {
            thanhToan.setSoTien(BigDecimal.ZERO);
        }

        thanhToanService.save(thanhToan);

        return "redirect:/thanh-toan";
    }
}