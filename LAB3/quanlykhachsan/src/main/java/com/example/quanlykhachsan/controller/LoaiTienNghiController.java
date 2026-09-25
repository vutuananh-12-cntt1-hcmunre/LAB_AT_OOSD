package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.LoaiTienNghi;
import com.example.quanlykhachsan.service.LoaiTienNghiService;

@Controller
@RequestMapping("/loai-tien-nghi")
public class LoaiTienNghiController {

    private final LoaiTienNghiService service;

    public LoaiTienNghiController(LoaiTienNghiService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsLoaiTienNghi",
                service.getAll()
        );

        model.addAttribute(
                "loaiTienNghi",
                new LoaiTienNghi()
        );

        return "loaitiennghi";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("loaiTienNghi")
            LoaiTienNghi loaiTienNghi) {

        service.save(loaiTienNghi);

        return "redirect:/loai-tien-nghi";
    }
}