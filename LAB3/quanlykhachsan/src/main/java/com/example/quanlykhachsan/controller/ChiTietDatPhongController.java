package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.ChiTietDatPhong;
import com.example.quanlykhachsan.service.ChiTietDatPhongService;
import com.example.quanlykhachsan.service.PhieuDatPhongService;
import com.example.quanlykhachsan.service.PhongService;

@Controller
@RequestMapping("/chi-tiet-dat-phong")
public class ChiTietDatPhongController {

    private final ChiTietDatPhongService chiTietDatPhongService;
    private final PhieuDatPhongService phieuDatPhongService;
    private final PhongService phongService;

    public ChiTietDatPhongController(
            ChiTietDatPhongService chiTietDatPhongService,
            PhieuDatPhongService phieuDatPhongService,
            PhongService phongService) {

        this.chiTietDatPhongService = chiTietDatPhongService;
        this.phieuDatPhongService = phieuDatPhongService;
        this.phongService = phongService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsChiTietDatPhong",
                chiTietDatPhongService.getAll()
        );

        model.addAttribute(
                "dsPhieuDatPhong",
                phieuDatPhongService.getAll()
        );

        model.addAttribute(
                "dsPhong",
                phongService.getAllPhong()
        );

        model.addAttribute(
                "chiTietDatPhong",
                new ChiTietDatPhong()
        );

        return "chitietdatphong";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("chiTietDatPhong")
            ChiTietDatPhong chiTietDatPhong) {

        chiTietDatPhongService.save(chiTietDatPhong);

        return "redirect:/chi-tiet-dat-phong";
    }
}