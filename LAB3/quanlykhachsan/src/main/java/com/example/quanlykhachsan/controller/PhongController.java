package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.Phong;
import com.example.quanlykhachsan.service.KhuVucService;
import com.example.quanlykhachsan.service.PhongService;

@Controller
@RequestMapping("/phong")
public class PhongController {

    private final PhongService phongService;
    private final KhuVucService khuVucService;

    public PhongController(
            PhongService phongService,
            KhuVucService khuVucService) {

        this.phongService = phongService;
        this.khuVucService = khuVucService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("dsPhong", phongService.getAllPhong());

        model.addAttribute("dsKhuVuc", khuVucService.getAll());

        model.addAttribute("phong", new Phong());

        return "phong";
    }

    @PostMapping("/them")
    public String themPhong(
            @ModelAttribute("phong") Phong phong) {

        phong.setTrangThai("Trống");

        phongService.save(phong);

        return "redirect:/phong";
    }
}