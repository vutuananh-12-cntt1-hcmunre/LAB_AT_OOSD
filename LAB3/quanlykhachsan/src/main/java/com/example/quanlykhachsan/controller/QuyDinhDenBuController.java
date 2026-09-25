package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.QuyDinhDenBu;
import com.example.quanlykhachsan.service.LoaiTienNghiService;
import com.example.quanlykhachsan.service.QuyDinhDenBuService;

@Controller
@RequestMapping("/quy-dinh-den-bu")
public class QuyDinhDenBuController {

    private final QuyDinhDenBuService quyDinhDenBuService;
    private final LoaiTienNghiService loaiTienNghiService;

    public QuyDinhDenBuController(
            QuyDinhDenBuService quyDinhDenBuService,
            LoaiTienNghiService loaiTienNghiService) {

        this.quyDinhDenBuService = quyDinhDenBuService;
        this.loaiTienNghiService = loaiTienNghiService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsQuyDinh",
                quyDinhDenBuService.getAll()
        );

        model.addAttribute(
                "dsLoaiTienNghi",
                loaiTienNghiService.getAll()
        );

        model.addAttribute(
                "quyDinhDenBu",
                new QuyDinhDenBu()
        );

        return "quydinhdenbu";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("quyDinhDenBu")
            QuyDinhDenBu quyDinhDenBu) {

        quyDinhDenBuService.save(quyDinhDenBu);

        return "redirect:/quy-dinh-den-bu";
    }
}