package com.example.quanlythuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlythuvien.entity.NhaXuatBan;
import com.example.quanlythuvien.service.NhaXuatBanService;

@Controller
@RequestMapping("/nhaxuatban")
public class NhaXuatBanController {

    private final NhaXuatBanService nhaXuatBanService;

    public NhaXuatBanController(NhaXuatBanService nhaXuatBanService) {
        this.nhaXuatBanService = nhaXuatBanService;
    }

    @GetMapping
    public String danhSach(Model model) {

        model.addAttribute(
                "danhSachNhaXuatBan",
                nhaXuatBanService.findAll()
        );

        return "nhaxuatban/list";
    }

    @GetMapping("/them")
    public String hienThiFormThem(Model model) {

        model.addAttribute(
                "nhaXuatBan",
                new NhaXuatBan()
        );

        model.addAttribute(
                "tieuDe",
                "Thêm nhà xuất bản"
        );

        return "nhaxuatban/form";
    }

    @PostMapping("/luu")
    public String luu(
            @ModelAttribute("nhaXuatBan") NhaXuatBan nhaXuatBan,
            Model model) {

        if (nhaXuatBan.getMaNhaXuatBan() == null
                || nhaXuatBan.getMaNhaXuatBan().trim().isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Mã nhà xuất bản không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhà xuất bản"
            );

            return "nhaxuatban/form";
        }

        if (nhaXuatBan.getTenNhaXuatBan() == null
                || nhaXuatBan.getTenNhaXuatBan().trim().isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Tên nhà xuất bản không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhà xuất bản"
            );

            return "nhaxuatban/form";
        }

        String maNhaXuatBan =
                nhaXuatBan.getMaNhaXuatBan().trim();

        nhaXuatBan.setMaNhaXuatBan(maNhaXuatBan);

        nhaXuatBan.setTenNhaXuatBan(
                nhaXuatBan.getTenNhaXuatBan().trim()
        );

        if (nhaXuatBan.getDiaChi() != null) {
            nhaXuatBan.setDiaChi(
                    nhaXuatBan.getDiaChi().trim()
            );
        }

        if (nhaXuatBan.getSoDienThoai() != null) {
            nhaXuatBan.setSoDienThoai(
                    nhaXuatBan.getSoDienThoai().trim()
            );
        }

        nhaXuatBanService.save(nhaXuatBan);

        return "redirect:/nhaxuatban";
    }

    @GetMapping("/sua/{id}")
    public String hienThiFormSua(
            @PathVariable("id") String id,
            Model model) {

        NhaXuatBan nhaXuatBan =
                nhaXuatBanService
                        .findById(id)
                        .orElse(null);

        if (nhaXuatBan == null) {
            return "redirect:/nhaxuatban";
        }

        model.addAttribute(
                "nhaXuatBan",
                nhaXuatBan
        );

        model.addAttribute(
                "tieuDe",
                "Sửa nhà xuất bản"
        );

        return "nhaxuatban/form";
    }

    @GetMapping("/xoa/{id}")
    public String xoa(
            @PathVariable("id") String id) {

        if (nhaXuatBanService.existsById(id)) {
            nhaXuatBanService.deleteById(id);
        }

        return "redirect:/nhaxuatban";
    }
}