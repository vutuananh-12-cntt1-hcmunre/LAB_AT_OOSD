package com.example.quanlythuvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlythuvien.entity.DocGia;
import com.example.quanlythuvien.entity.TheDocGia;
import com.example.quanlythuvien.service.DocGiaService;
import com.example.quanlythuvien.service.TheDocGiaService;

@Controller
@RequestMapping("/thedocgia")
public class TheDocGiaController {

    private final TheDocGiaService theDocGiaService;
    private final DocGiaService docGiaService;

    public TheDocGiaController(
            TheDocGiaService theDocGiaService,
            DocGiaService docGiaService) {

        this.theDocGiaService = theDocGiaService;
        this.docGiaService = docGiaService;
    }

    // ==============================
    // DANH SÁCH THẺ
    // ==============================

    @GetMapping
    public String danhSach(Model model) {

        model.addAttribute(
                "danhSachThe",
                theDocGiaService.findAll()
        );

        return "thedocgia/list";
    }

    // ==============================
    // FORM THÊM
    // ==============================

    @GetMapping("/them")
    public String them(Model model) {

        model.addAttribute("theDocGia", new TheDocGia());

        model.addAttribute(
                "danhSachDocGia",
                docGiaService.findAll()
        );

        model.addAttribute(
                "tieuDe",
                "Cấp thẻ độc giả"
        );

        return "thedocgia/form";
    }

    // ==============================
    // LƯU
    // ==============================

    @PostMapping("/luu")
    public String luu(
            @ModelAttribute("theDocGia") TheDocGia theDocGia,
            @RequestParam("maDocGia") String maDocGia,
            Model model) {

        // Kiểm tra mã thẻ
        if (theDocGia.getMaThe() == null ||
                theDocGia.getMaThe().trim().isEmpty()) {

            model.addAttribute(
                    "loi",
                    "Mã thẻ không được để trống."
            );

            model.addAttribute(
                    "danhSachDocGia",
                    docGiaService.findAll()
            );

            model.addAttribute(
                    "tieuDe",
                    "Cấp thẻ độc giả"
            );

            return "thedocgia/form";
        }

        // Tìm độc giả
        DocGia docGia =
                docGiaService.findById(maDocGia).orElse(null);

        if (docGia == null) {

            model.addAttribute(
                    "loi",
                    "Không tìm thấy độc giả."
            );

            model.addAttribute(
                    "danhSachDocGia",
                    docGiaService.findAll()
            );

            model.addAttribute(
                    "tieuDe",
                    "Cấp thẻ độc giả"
            );

            return "thedocgia/form";
        }

        // Nếu thêm mới thì kiểm tra BR02
        if (!theDocGiaService.existsById(theDocGia.getMaThe())) {

            if (theDocGiaService.daCoTheDangHoatDong(maDocGia)) {

                model.addAttribute(
                        "loi",
                        "Độc giả này đã có thẻ đang hoạt động."
                );

                model.addAttribute(
                        "danhSachDocGia",
                        docGiaService.findAll()
                );

                model.addAttribute(
                        "tieuDe",
                        "Cấp thẻ độc giả"
                );

                return "thedocgia/form";
            }
        }

        // Kiểm tra ngày
        if (theDocGia.getNgayCap() == null ||
                theDocGia.getHanSuDung() == null) {

            model.addAttribute(
                    "loi",
                    "Ngày cấp và hạn sử dụng không được để trống."
            );

            model.addAttribute(
                    "danhSachDocGia",
                    docGiaService.findAll()
            );

            model.addAttribute(
                    "tieuDe",
                    "Cấp thẻ độc giả"
            );

            return "thedocgia/form";
        }

        if (theDocGia.getHanSuDung()
                .isBefore(theDocGia.getNgayCap())) {

            model.addAttribute(
                    "loi",
                    "Hạn sử dụng không được trước ngày cấp."
            );

            model.addAttribute(
                    "danhSachDocGia",
                    docGiaService.findAll()
            );

            model.addAttribute(
                    "tieuDe",
                    "Cấp thẻ độc giả"
            );

            return "thedocgia/form";
        }

        theDocGia.setMaThe(
                theDocGia.getMaThe().trim()
        );

        theDocGia.setDocGia(docGia);

        // Nếu checkbox không được chọn thì false
        if (theDocGia.getDaDongLePhi() == null) {
            theDocGia.setDaDongLePhi(false);
        }

        if (theDocGia.getTrangThai() == null) {
            theDocGia.setTrangThai(true);
        }

        theDocGiaService.save(theDocGia);

        return "redirect:/thedocgia";
    }

    // ==============================
    // SỬA
    // ==============================

    @GetMapping("/sua/{id}")
    public String sua(
            @PathVariable("id") String id,
            Model model) {

        TheDocGia theDocGia =
                theDocGiaService.findById(id).orElse(null);

        if (theDocGia == null) {
            return "redirect:/thedocgia";
        }

        model.addAttribute(
                "theDocGia",
                theDocGia
        );

        model.addAttribute(
                "danhSachDocGia",
                docGiaService.findAll()
        );

        model.addAttribute(
                "tieuDe",
                "Sửa thẻ độc giả"
        );

        return "thedocgia/form";
    }

    // ==============================
    // XÓA
    // ==============================

    @GetMapping("/xoa/{id}")
    public String xoa(
            @PathVariable("id") String id) {

        try {

            theDocGiaService.deleteById(id);

        } catch (Exception e) {

            return "redirect:/thedocgia?loi=khongthexoa";
        }

        return "redirect:/thedocgia";
    }

    // ==============================
    // VÔ HIỆU HÓA THẺ
    // ==============================

    @GetMapping("/vohieuhoa/{id}")
    public String voHieuHoa(
            @PathVariable("id") String id) {

        TheDocGia theDocGia =
                theDocGiaService.findById(id).orElse(null);

        if (theDocGia != null) {

            theDocGia.setTrangThai(false);

            theDocGiaService.save(theDocGia);
        }

        return "redirect:/thedocgia";
    }
}