package com.example.quanlythuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlythuvien.entity.DocGia;
import com.example.quanlythuvien.service.DocGiaService;

@Controller
@RequestMapping("/docgia")
public class DocGiaController {

    private final DocGiaService docGiaService;

    public DocGiaController(DocGiaService docGiaService) {
        this.docGiaService = docGiaService;
    }

    // Hiển thị danh sách độc giả
    @GetMapping
    public String danhSach(Model model) {
        model.addAttribute("danhSachDocGia", docGiaService.findAll());
        return "docgia/list";
    }

    // Hiển thị form thêm
    @GetMapping("/them")
    public String them(Model model) {
        model.addAttribute("docGia", new DocGia());
        model.addAttribute("tieuDe", "Thêm độc giả");
        return "docgia/form";
    }

    // Lưu độc giả
    @PostMapping("/luu")
    public String luu(
            @ModelAttribute("docGia") DocGia docGia,
            Model model) {

        if (docGia.getMaDocGia() == null ||
                docGia.getMaDocGia().trim().isEmpty()) {

            model.addAttribute("loi", "Mã độc giả không được để trống.");
            model.addAttribute("tieuDe", "Thêm độc giả");
            return "docgia/form";
        }

        if (docGia.getHoTen() == null ||
                docGia.getHoTen().trim().isEmpty()) {

            model.addAttribute("loi", "Họ tên không được để trống.");
            model.addAttribute("tieuDe", "Thêm độc giả");
            return "docgia/form";
        }

        docGia.setMaDocGia(docGia.getMaDocGia().trim());
        docGia.setHoTen(docGia.getHoTen().trim());

        if (docGia.getDiaChi() != null) {
            docGia.setDiaChi(docGia.getDiaChi().trim());
        }

        if (docGia.getSoDienThoai() != null) {
            docGia.setSoDienThoai(docGia.getSoDienThoai().trim());
        }

        if (docGia.getEmail() != null) {
            docGia.setEmail(docGia.getEmail().trim());
        }

        docGiaService.save(docGia);

        return "redirect:/docgia";
    }

    // Hiển thị form sửa
    @GetMapping("/sua/{id}")
    public String sua(
            @PathVariable("id") String id,
            Model model) {

        DocGia docGia = docGiaService.findById(id).orElse(null);

        if (docGia == null) {
            return "redirect:/docgia";
        }

        model.addAttribute("docGia", docGia);
        model.addAttribute("tieuDe", "Sửa độc giả");

        return "docgia/form";
    }

    // Xóa độc giả
    @GetMapping("/xoa/{id}")
    public String xoa(@PathVariable("id") String id) {

        try {
            docGiaService.deleteById(id);
        } catch (Exception e) {
            return "redirect:/docgia?loi=khongthexoa";
        }

        return "redirect:/docgia";
    }
}