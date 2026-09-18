package com.example.quanlythuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlythuvien.entity.DauSach;
import com.example.quanlythuvien.service.DauSachService;
import com.example.quanlythuvien.service.NhaXuatBanService;
import com.example.quanlythuvien.service.TheLoaiService;

@Controller
@RequestMapping("/dausach")
public class DauSachController {

    private final DauSachService dauSachService;
    private final TheLoaiService theLoaiService;
    private final NhaXuatBanService nhaXuatBanService;

    public DauSachController(
            DauSachService dauSachService,
            TheLoaiService theLoaiService,
            NhaXuatBanService nhaXuatBanService) {

        this.dauSachService = dauSachService;
        this.theLoaiService = theLoaiService;
        this.nhaXuatBanService = nhaXuatBanService;
    }

    @GetMapping
    public String danhSach(Model model) {

        model.addAttribute(
                "danhSachDauSach",
                dauSachService.findAll()
        );

        return "dausach/list";
    }

    @GetMapping("/them")
    public String hienThiFormThem(Model model) {

        model.addAttribute(
                "dauSach",
                new DauSach()
        );

        model.addAttribute(
                "danhSachTheLoai",
                theLoaiService.findAll()
        );

        model.addAttribute(
                "danhSachNhaXuatBan",
                nhaXuatBanService.findAll()
        );

        model.addAttribute(
                "tieuDe",
                "Thêm đầu sách"
        );

        return "dausach/form";
    }

    @PostMapping("/luu")
    public String luu(
            @ModelAttribute("dauSach") DauSach dauSach,
            Model model) {

        if (dauSach.getMaDauSach() == null
                || dauSach.getMaDauSach().trim().isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Mã đầu sách không được để trống."
            );

            napDuLieuForm(model);

            model.addAttribute(
                    "tieuDe",
                    "Thêm đầu sách"
            );

            return "dausach/form";
        }

        if (dauSach.getTenSach() == null
                || dauSach.getTenSach().trim().isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Tên sách không được để trống."
            );

            napDuLieuForm(model);

            model.addAttribute(
                    "tieuDe",
                    "Thêm đầu sách"
            );

            return "dausach/form";
        }

        if (dauSach.getTheLoai() == null) {

            model.addAttribute(
                    "loi",
                    "Vui lòng chọn thể loại."
            );

            napDuLieuForm(model);

            model.addAttribute(
                    "tieuDe",
                    "Thêm đầu sách"
            );

            return "dausach/form";
        }

        if (dauSach.getNhaXuatBan() == null) {

            model.addAttribute(
                    "loi",
                    "Vui lòng chọn nhà xuất bản."
            );

            napDuLieuForm(model);

            model.addAttribute(
                    "tieuDe",
                    "Thêm đầu sách"
            );

            return "dausach/form";
        }

        if (dauSach.getSoLuongHienCo() == null
                || dauSach.getSoLuongHienCo() < 0) {

            model.addAttribute(
                    "loi",
                    "Số lượng hiện có phải lớn hơn hoặc bằng 0."
            );

            napDuLieuForm(model);

            model.addAttribute(
                    "tieuDe",
                    "Thêm đầu sách"
            );

            return "dausach/form";
        }

        dauSach.setMaDauSach(
                dauSach.getMaDauSach().trim()
        );

        dauSach.setTenSach(
                dauSach.getTenSach().trim()
        );

        dauSachService.save(dauSach);

        return "redirect:/dausach";
    }

    @GetMapping("/sua/{id}")
    public String hienThiFormSua(
            @PathVariable("id") String id,
            Model model) {

        DauSach dauSach = dauSachService
                .findById(id)
                .orElse(null);

        if (dauSach == null) {
            return "redirect:/dausach";
        }

        model.addAttribute(
                "dauSach",
                dauSach
        );

        model.addAttribute(
                "danhSachTheLoai",
                theLoaiService.findAll()
        );

        model.addAttribute(
                "danhSachNhaXuatBan",
                nhaXuatBanService.findAll()
        );

        model.addAttribute(
                "tieuDe",
                "Sửa đầu sách"
        );

        return "dausach/form";
    }

    @GetMapping("/xoa/{id}")
    public String xoa(
            @PathVariable("id") String id,
            Model model) {

        if (dauSachService.existsById(id)) {

            try {

                dauSachService.deleteById(id);

            } catch (Exception e) {

                return "redirect:/dausach?loi=khongthexoa";
            }
        }

        return "redirect:/dausach";
    }

    private void napDuLieuForm(Model model) {

        model.addAttribute(
                "danhSachTheLoai",
                theLoaiService.findAll()
        );

        model.addAttribute(
                "danhSachNhaXuatBan",
                nhaXuatBanService.findAll()
        );
    }
}