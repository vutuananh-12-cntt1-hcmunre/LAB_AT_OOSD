package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.KhuVuc;
import com.example.quanlykhachsan.service.KhuVucService;

@Controller
@RequestMapping("/khu-vuc")
public class KhuVucController {

    private final KhuVucService khuVucService;

    public KhuVucController(KhuVucService khuVucService) {
        this.khuVucService = khuVucService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("dsKhuVuc", khuVucService.getAll());
        model.addAttribute("khuVuc", new KhuVuc());

        return "khuvuc";
    }

    @PostMapping("/them")
    public String them(@ModelAttribute("khuVuc") KhuVuc khuVuc) {
        khuVucService.save(khuVuc);
        return "redirect:/khu-vuc";
    }
}