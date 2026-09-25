package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.NhanVien;
import com.example.quanlykhachsan.service.NhanVienService;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {

    private final NhanVienService service;

    public NhanVienController(NhanVienService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsNhanVien",
                service.getAll()
        );

        model.addAttribute(
                "nhanVien",
                new NhanVien()
        );

        return "nhanvien";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("nhanVien")
            NhanVien nhanVien) {

        service.save(nhanVien);

        return "redirect:/nhan-vien";
    }
}