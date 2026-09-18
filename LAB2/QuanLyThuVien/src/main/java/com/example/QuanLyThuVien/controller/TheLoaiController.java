package com.example.quanlythuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlythuvien.entity.TheLoai;
import com.example.quanlythuvien.service.TheLoaiService;

@Controller
@RequestMapping("/theloai")
public class TheLoaiController {

    private final TheLoaiService theLoaiService;

    public TheLoaiController(TheLoaiService theLoaiService) {
        this.theLoaiService = theLoaiService;
    }

    @GetMapping
    public String danhSach(Model model) {

        model.addAttribute(
                "danhSachTheLoai",
                theLoaiService.findAll()
        );

        return "theloai/list";
    }

    @GetMapping("/them")
    public String hienThiFormThem(Model model) {

        model.addAttribute(
                "theLoai",
                new TheLoai()
        );

        model.addAttribute(
                "tieuDe",
                "Thêm thể loại"
        );

        return "theloai/form";
    }

    @PostMapping("/luu")
    public String luuTheLoai(
            @ModelAttribute("theLoai") TheLoai theLoai,
            Model model) {

        if (theLoai.getMaTheLoai() == null
                || theLoai.getMaTheLoai().trim().isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Mã thể loại không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm thể loại"
            );

            return "theloai/form";
        }

        if (theLoai.getTenTheLoai() == null
                || theLoai.getTenTheLoai().trim().isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Tên thể loại không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm thể loại"
            );

            return "theloai/form";
        }

        String maTheLoai = theLoai
                .getMaTheLoai()
                .trim();

        /*
         * Nếu ID chưa tồn tại -> thêm mới.
         * Nếu ID đã tồn tại -> cập nhật.
         */
        theLoai.setMaTheLoai(maTheLoai);
        theLoai.setTenTheLoai(
                theLoai.getTenTheLoai().trim()
        );

        theLoaiService.save(theLoai);

        return "redirect:/theloai";
    }

    @GetMapping("/sua/{id}")
    public String hienThiFormSua(
            @PathVariable("id") String id,
            Model model) {

        TheLoai theLoai = theLoaiService
                .findById(id)
                .orElse(null);

        if (theLoai == null) {
            return "redirect:/theloai";
        }

        model.addAttribute(
                "theLoai",
                theLoai
        );

        model.addAttribute(
                "tieuDe",
                "Sửa thể loại"
        );

        return "theloai/form";
    }

    @GetMapping("/xoa/{id}")
    public String xoa(
            @PathVariable("id") String id) {

        if (theLoaiService.existsById(id)) {
            theLoaiService.deleteById(id);
        }

        return "redirect:/theloai";
    }
}