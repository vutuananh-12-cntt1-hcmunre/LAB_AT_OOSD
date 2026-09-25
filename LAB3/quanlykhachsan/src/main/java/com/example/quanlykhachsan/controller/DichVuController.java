package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.DichVu;
import com.example.quanlykhachsan.service.DichVuService;

@Controller
@RequestMapping("/dich-vu")
public class DichVuController {

    private final DichVuService dichVuService;

    public DichVuController(DichVuService dichVuService) {
        this.dichVuService = dichVuService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("dsDichVu", dichVuService.getAll());
        model.addAttribute("dichVu", new DichVu());

        return "dichvu";
    }

    @PostMapping("/them")
    public String them(@ModelAttribute("dichVu") DichVu dichVu) {

        dichVuService.save(dichVu);

        return "redirect:/dich-vu";
    }
}