package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.TienNghi;
import com.example.quanlykhachsan.service.LoaiTienNghiService;
import com.example.quanlykhachsan.service.TienNghiService;

@Controller
@RequestMapping("/tien-nghi")
public class TienNghiController {

    private final TienNghiService tienNghiService;
    private final LoaiTienNghiService loaiTienNghiService;

    public TienNghiController(
            TienNghiService tienNghiService,
            LoaiTienNghiService loaiTienNghiService) {

        this.tienNghiService = tienNghiService;
        this.loaiTienNghiService = loaiTienNghiService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsTienNghi",
                tienNghiService.getAll()
        );

        model.addAttribute(
                "dsLoaiTienNghi",
                loaiTienNghiService.getAll()
        );

        model.addAttribute(
                "tienNghi",
                new TienNghi()
        );

        return "tiennghi";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("tienNghi")
            TienNghi tienNghi) {

        if (tienNghi.getTinhTrangHienTai() == null
                || tienNghi.getTinhTrangHienTai().isBlank()) {

            tienNghi.setTinhTrangHienTai("Tốt");
        }

        tienNghiService.save(tienNghi);

        return "redirect:/tien-nghi";
    }
}