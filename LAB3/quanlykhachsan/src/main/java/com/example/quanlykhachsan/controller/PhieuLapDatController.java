package com.example.quanlykhachsan.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlykhachsan.entity.PhieuLapDat;
import com.example.quanlykhachsan.service.NhanVienService;
import com.example.quanlykhachsan.service.PhieuLapDatService;
import com.example.quanlykhachsan.service.PhongService;
import com.example.quanlykhachsan.service.TienNghiService;

@Controller
@RequestMapping("/lap-dat")
public class PhieuLapDatController {

    private final PhieuLapDatService phieuLapDatService;
    private final PhongService phongService;
    private final TienNghiService tienNghiService;
    private final NhanVienService nhanVienService;

    public PhieuLapDatController(
            PhieuLapDatService phieuLapDatService,
            PhongService phongService,
            TienNghiService tienNghiService,
            NhanVienService nhanVienService) {

        this.phieuLapDatService = phieuLapDatService;
        this.phongService = phongService;
        this.tienNghiService = tienNghiService;
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("dsPhieuLapDat", phieuLapDatService.getAll());

        model.addAttribute("dsPhong", phongService.getAllPhong());

        model.addAttribute("dsTienNghi", tienNghiService.getAll());

        // Lấy danh sách nhân viên từ database
        model.addAttribute("dsNhanVien", nhanVienService.getAll());

        model.addAttribute("phieuLapDat", new PhieuLapDat());

        return "phieulapdat";
    }

    @PostMapping("/them")
    public String them(@ModelAttribute("phieuLapDat") PhieuLapDat phieuLapDat) {

        if (phieuLapDat.getNgayLap() == null) {
            phieuLapDat.setNgayLap(LocalDate.now());
        }

        if (phieuLapDat.getTinhTrang() == null
                || phieuLapDat.getTinhTrang().isBlank()) {

            phieuLapDat.setTinhTrang("Đang sử dụng");
        }

        phieuLapDatService.save(phieuLapDat);

        return "redirect:/lap-dat";
    }
}