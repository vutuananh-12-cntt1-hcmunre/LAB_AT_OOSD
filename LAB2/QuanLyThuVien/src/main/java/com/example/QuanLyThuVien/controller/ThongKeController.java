package com.example.quanlythuvien.controller;

import com.example.quanlythuvien.service.ThongKeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thongke")
public class ThongKeController {

    private final ThongKeService thongKeService;

    public ThongKeController(
            ThongKeService thongKeService) {

        this.thongKeService = thongKeService;
    }

    // ==========================================
    // TRANG THỐNG KÊ
    // ==========================================

    @GetMapping
    public String thongKe(Model model) {

        model.addAttribute(
                "tongSoPhieuMuon",
                thongKeService.tongSoPhieuMuon()
        );

        model.addAttribute(
                "tongSoSachDaMuon",
                thongKeService.tongSoSachDaMuon()
        );

        model.addAttribute(
                "soSachDaTra",
                thongKeService.soSachDaTra()
        );

        model.addAttribute(
                "soSachDangMuon",
                thongKeService.soSachDangMuon()
        );

        model.addAttribute(
                "soSachQuaHan",
                thongKeService.soSachQuaHan()
        );

        model.addAttribute(
                "soSachBiPhat",
                thongKeService.soSachBiPhat()
        );

        model.addAttribute(
                "tongTienPhat",
                thongKeService.tongTienPhat()
        );

        return "thongke/index";
    }
}