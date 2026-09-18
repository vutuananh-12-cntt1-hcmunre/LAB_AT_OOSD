package com.example.quanlythuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlythuvien.entity.NhanVien;
import com.example.quanlythuvien.service.NhanVienService;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    private final NhanVienService nhanVienService;

    public NhanVienController(
            NhanVienService nhanVienService) {

        this.nhanVienService = nhanVienService;
    }

    // ==========================================
    // DANH SÁCH NHÂN VIÊN
    // ==========================================

    @GetMapping
    public String danhSach(Model model) {

        model.addAttribute(
                "danhSachNhanVien",
                nhanVienService.findAll()
        );

        return "nhanvien/list";
    }

    // ==========================================
    // FORM THÊM NHÂN VIÊN
    // ==========================================

    @GetMapping("/them")
    public String them(Model model) {

        model.addAttribute(
                "nhanVien",
                new NhanVien()
        );

        model.addAttribute(
                "tieuDe",
                "Thêm nhân viên"
        );

        return "nhanvien/form";
    }

    // ==========================================
    // LƯU NHÂN VIÊN
    // ==========================================

    @PostMapping("/luu")
    public String luu(
            @ModelAttribute("nhanVien")
            NhanVien nhanVien,
            Model model) {

        // --------------------------------------
        // Kiểm tra mã nhân viên
        // --------------------------------------

        if (nhanVien.getMaNhanVien() == null ||
                nhanVien.getMaNhanVien()
                        .trim()
                        .isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Mã nhân viên không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhân viên"
            );

            return "nhanvien/form";
        }

        // --------------------------------------
        // Kiểm tra họ
        // --------------------------------------

        if (nhanVien.getHo() == null ||
                nhanVien.getHo()
                        .trim()
                        .isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Họ không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhân viên"
            );

            return "nhanvien/form";
        }

        // --------------------------------------
        // Kiểm tra tên
        // --------------------------------------

        if (nhanVien.getTen() == null ||
                nhanVien.getTen()
                        .trim()
                        .isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Tên không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhân viên"
            );

            return "nhanvien/form";
        }

        // --------------------------------------
        // Kiểm tra phái
        // --------------------------------------

        if (nhanVien.getPhai() == null ||
                nhanVien.getPhai()
                        .trim()
                        .isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Phái không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhân viên"
            );

            return "nhanvien/form";
        }

        // --------------------------------------
        // Kiểm tra ngày sinh
        // --------------------------------------

        if (nhanVien.getNgaySinh() == null) {

            model.addAttribute(
                    "loi",
                    "Ngày sinh không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhân viên"
            );

            return "nhanvien/form";
        }

        // --------------------------------------
        // Kiểm tra chức vụ
        // --------------------------------------

        if (nhanVien.getChucVu() == null ||
                nhanVien.getChucVu()
                        .trim()
                        .isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Chức vụ không được để trống."
            );

            model.addAttribute(
                    "tieuDe",
                    "Thêm nhân viên"
            );

            return "nhanvien/form";
        }

        // --------------------------------------
        // Chuẩn hóa dữ liệu
        // --------------------------------------

        nhanVien.setMaNhanVien(
                nhanVien.getMaNhanVien()
                        .trim()
        );

        nhanVien.setHo(
                nhanVien.getHo()
                        .trim()
        );

        nhanVien.setTen(
                nhanVien.getTen()
                        .trim()
        );

        nhanVien.setPhai(
                nhanVien.getPhai()
                        .trim()
        );

        nhanVien.setChucVu(
                nhanVien.getChucVu()
                        .trim()
        );

        if (nhanVien.getSoDienThoai() != null) {

            nhanVien.setSoDienThoai(
                    nhanVien.getSoDienThoai()
                            .trim()
            );
        }

        // --------------------------------------
        // Lưu
        //
        // JpaRepository.save():
        // - ID mới      -> INSERT
        // - ID tồn tại  -> UPDATE
        // --------------------------------------

        nhanVienService.save(nhanVien);

        return "redirect:/nhanvien";
    }

    // ==========================================
    // FORM SỬA NHÂN VIÊN
    // ==========================================

    @GetMapping("/sua/{id}")
    public String sua(
            @PathVariable("id")
            String id,
            Model model) {

        NhanVien nhanVien =
                nhanVienService
                        .findById(id)
                        .orElse(null);

        if (nhanVien == null) {

            return "redirect:/nhanvien";
        }

        model.addAttribute(
                "nhanVien",
                nhanVien
        );

        model.addAttribute(
                "tieuDe",
                "Sửa nhân viên"
        );

        return "nhanvien/form";
    }

    // ==========================================
    // XÓA NHÂN VIÊN
    // ==========================================

    @GetMapping("/xoa/{id}")
    public String xoa(
            @PathVariable("id")
            String id) {

        try {

            nhanVienService.deleteById(id);

        } catch (Exception e) {

            return "redirect:/nhanvien?loi=khongthexoa";
        }

        return "redirect:/nhanvien";
    }
}