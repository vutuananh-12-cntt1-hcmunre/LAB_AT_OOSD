package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.KhachHang;
import com.example.quanlykhachsan.service.KhachHangService;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {

    private final KhachHangService service;

    public KhachHangController(KhachHangService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("dsKhachHang", service.getAll());
        model.addAttribute("khachHang", new KhachHang());

        return "khachhang";
    }

    @PostMapping("/them")
    public String them(@ModelAttribute("khachHang") KhachHang khachHang) {

        service.save(khachHang);

        return "redirect:/khach-hang";
    }
}