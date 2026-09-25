package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.NguoiLuuTru;
import com.example.quanlykhachsan.service.ChiTietDatPhongService;
import com.example.quanlykhachsan.service.NguoiLuuTruService;

@Controller
@RequestMapping("/nguoi-luu-tru")
public class NguoiLuuTruController {

    private final NguoiLuuTruService nguoiLuuTruService;
    private final ChiTietDatPhongService chiTietDatPhongService;

    public NguoiLuuTruController(
            NguoiLuuTruService nguoiLuuTruService,
            ChiTietDatPhongService chiTietDatPhongService) {

        this.nguoiLuuTruService = nguoiLuuTruService;
        this.chiTietDatPhongService = chiTietDatPhongService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsNguoiLuuTru",
                nguoiLuuTruService.getAll()
        );

        model.addAttribute(
                "dsChiTietDatPhong",
                chiTietDatPhongService.getAll()
        );

        model.addAttribute(
                "nguoiLuuTru",
                new NguoiLuuTru()
        );

        return "nguoiluutru";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("nguoiLuuTru")
            NguoiLuuTru nguoiLuuTru) {

        nguoiLuuTruService.save(nguoiLuuTru);

        return "redirect:/nguoi-luu-tru";
    }
}