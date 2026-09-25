package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.ChiTietPhieuSuDungDV;
import com.example.quanlykhachsan.service.ChiTietPhieuSuDungDVService;
import com.example.quanlykhachsan.service.DichVuService;
import com.example.quanlykhachsan.service.PhieuSuDungDVService;

@Controller
@RequestMapping("/chi-tiet-su-dung-dich-vu")
public class ChiTietPhieuSuDungDVController {

    private final ChiTietPhieuSuDungDVService chiTietService;
    private final PhieuSuDungDVService phieuSuDungDVService;
    private final DichVuService dichVuService;

    public ChiTietPhieuSuDungDVController(
            ChiTietPhieuSuDungDVService chiTietService,
            PhieuSuDungDVService phieuSuDungDVService,
            DichVuService dichVuService) {

        this.chiTietService = chiTietService;
        this.phieuSuDungDVService = phieuSuDungDVService;
        this.dichVuService = dichVuService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsChiTiet",
                chiTietService.getAll()
        );

        model.addAttribute(
                "dsPhieuSuDungDV",
                phieuSuDungDVService.getAll()
        );

        model.addAttribute(
                "dsDichVu",
                dichVuService.getAll()
        );

        model.addAttribute(
                "chiTiet",
                new ChiTietPhieuSuDungDV()
        );

        return "chitietphieusudungdv";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("chiTiet")
            ChiTietPhieuSuDungDV chiTiet) {

        chiTietService.save(chiTiet);

        return "redirect:/chi-tiet-su-dung-dich-vu";
    }
}