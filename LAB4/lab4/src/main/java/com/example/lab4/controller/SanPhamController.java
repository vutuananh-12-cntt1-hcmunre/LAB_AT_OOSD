package com.example.lab4.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lab4.entity.SanPham;
import com.example.lab4.service.SanPhamService;

@Controller
public class SanPhamController {

    private final SanPhamService sanPhamService;

    public SanPhamController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    // Danh sách sản phẩm
    @GetMapping("/san-pham")
    public String danhSachSanPham(
            @RequestParam(required = false) String nhom,
            Model model) {

        List<SanPham> danhSach;

        if (nhom != null && !nhom.isBlank()) {
            danhSach = sanPhamService.getTheoNhom(nhom);
        } else {
            danhSach = sanPhamService.getTatCa();
        }

        model.addAttribute("danhSachSanPham", danhSach);
        model.addAttribute("maNhomDangChon", nhom);

        return "sanpham";
    }

    // Chi tiết sản phẩm
    @GetMapping("/chi-tiet-san-pham")
    public String chiTietSanPham(
            @RequestParam String maSP,
            Model model) {

        SanPham sanPham = sanPhamService
                .getByMaSP(maSP)
                .orElse(null);

        if (sanPham == null) {
            return "redirect:/san-pham";
        }

        model.addAttribute("sanPham", sanPham);

        return "chitietsanpham";
    }
}