package com.example.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.lab4.entity.KhachHang;
import com.example.lab4.repository.KhachHangRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class DangNhapController {

    private final KhachHangRepository khachHangRepository;

    public DangNhapController(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    @GetMapping("/dang-nhap")
    public String hienThiFormDangNhap(
            @RequestParam(value = "success", required = false) String success,
            Model model) {

        if (success != null) {
            model.addAttribute(
                    "thongBao",
                    "Đăng ký tài khoản thành công. Vui lòng đăng nhập."
            );
        }

        return "dangnhap";
    }

    @PostMapping("/dang-nhap")
    public String dangNhap(
            @RequestParam("username") String username,
            @RequestParam("matKhau") String matKhau,
            HttpSession session,
            Model model) {

        KhachHang khachHang =
                khachHangRepository.findByUsername(username).orElse(null);

        if (khachHang == null) {
            model.addAttribute("loi", "Tên đăng nhập không tồn tại!");
            return "dangnhap";
        }

        if (!khachHang.getMatKhau().equals(matKhau)) {
            model.addAttribute("loi", "Mật khẩu không chính xác!");
            return "dangnhap";
        }

        // Lưu khách hàng đang đăng nhập vào Session
        session.setAttribute("khachHang", khachHang);

        return "redirect:/";
    }

    @GetMapping("/dang-xuat")
    public String dangXuat(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}