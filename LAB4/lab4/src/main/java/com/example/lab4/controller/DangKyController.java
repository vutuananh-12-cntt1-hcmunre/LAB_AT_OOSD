package com.example.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.lab4.entity.KhachHang;
import com.example.lab4.repository.KhachHangRepository;

@Controller
public class DangKyController {

    private final KhachHangRepository khachHangRepository;

    public DangKyController(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    @GetMapping("/dang-ky")
    public String hienThiForm(Model model) {

        model.addAttribute("khachHang", new KhachHang());

        return "dangky";
    }

    @PostMapping("/dang-ky")
    public String dangKy(KhachHang khachHang, Model model) {

        if (khachHangRepository.existsByUsername(khachHang.getUsername())) {
            model.addAttribute("loi", "Tên đăng nhập đã tồn tại!");
            return "dangky";
        }

        if (khachHang.getEmail() != null
                && !khachHang.getEmail().isBlank()
                && khachHangRepository.existsByEmail(khachHang.getEmail())) {

            model.addAttribute("loi", "Email đã được sử dụng!");
            return "dangky";
        }

        String maKH = "KH" + String.format(
                "%03d",
                khachHangRepository.count() + 1
        );

        khachHang.setMaKH(maKH);

        khachHangRepository.save(khachHang);

        return "redirect:/dang-nhap?success";
    }
}