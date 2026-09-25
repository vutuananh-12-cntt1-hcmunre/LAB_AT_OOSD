package com.example.quanlykhachsan.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.PhieuSuDungDV;
import com.example.quanlykhachsan.service.ChiTietDatPhongService;
import com.example.quanlykhachsan.service.NhanVienService;
import com.example.quanlykhachsan.service.PhieuSuDungDVService;

@Controller
@RequestMapping("/su-dung-dich-vu")
public class PhieuSuDungDVController {

    private final PhieuSuDungDVService phieuSuDungDVService;
    private final ChiTietDatPhongService chiTietDatPhongService;
    private final NhanVienService nhanVienService;

    public PhieuSuDungDVController(
            PhieuSuDungDVService phieuSuDungDVService,
            ChiTietDatPhongService chiTietDatPhongService,
            NhanVienService nhanVienService) {

        this.phieuSuDungDVService = phieuSuDungDVService;
        this.chiTietDatPhongService = chiTietDatPhongService;
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute(
                "dsPhieuSuDungDV",
                phieuSuDungDVService.getAll()
        );

        model.addAttribute(
                "dsChiTietDatPhong",
                chiTietDatPhongService.getAll()
        );

        model.addAttribute(
                "dsNhanVien",
                nhanVienService.getAll()
        );

        PhieuSuDungDV phieu = new PhieuSuDungDV();

        phieu.setNgaySuDung(LocalDate.now());

        model.addAttribute("phieuSuDungDV", phieu);

        return "phieusudungdv";
    }

    @PostMapping("/them")
    public String them(
            @ModelAttribute("phieuSuDungDV")
            PhieuSuDungDV phieuSuDungDV) {

        if (phieuSuDungDV.getNgaySuDung() == null) {
            phieuSuDungDV.setNgaySuDung(LocalDate.now());
        }

        phieuSuDungDVService.save(phieuSuDungDV);

        return "redirect:/su-dung-dich-vu";
    }
}