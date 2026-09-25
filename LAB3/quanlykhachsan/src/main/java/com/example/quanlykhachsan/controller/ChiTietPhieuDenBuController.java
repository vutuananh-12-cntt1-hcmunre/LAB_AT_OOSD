package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.ChiTietPhieuDenBu;
import com.example.quanlykhachsan.service.ChiTietPhieuDenBuService;
import com.example.quanlykhachsan.service.PhieuDenBuService;
import com.example.quanlykhachsan.service.TienNghiService;

@Controller
@RequestMapping("/chi-tiet-phieu-den-bu")
public class ChiTietPhieuDenBuController {

    private final ChiTietPhieuDenBuService chiTietService;
    private final PhieuDenBuService phieuDenBuService;
    private final TienNghiService tienNghiService;

    public ChiTietPhieuDenBuController(
            ChiTietPhieuDenBuService chiTietService,
            PhieuDenBuService phieuDenBuService,
            TienNghiService tienNghiService) {

        this.chiTietService = chiTietService;
        this.phieuDenBuService = phieuDenBuService;
        this.tienNghiService = tienNghiService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsChiTietPhieuDenBu",
                chiTietService.getAll()
        );

        model.addAttribute(
                "dsPhieuDenBu",
                phieuDenBuService.getAll()
        );

        model.addAttribute(
                "dsTienNghi",
                tienNghiService.getAll()
        );

        model.addAttribute(
                "chiTietPhieuDenBu",
                new ChiTietPhieuDenBu()
        );

        return "chitietphieudenbu";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("chiTietPhieuDenBu")
            ChiTietPhieuDenBu chiTietPhieuDenBu) {

        chiTietService.save(chiTietPhieuDenBu);

        return "redirect:/chi-tiet-phieu-den-bu";
    }
}