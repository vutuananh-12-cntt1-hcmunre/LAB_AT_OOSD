package com.example.lab5.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrangChuController {

    @GetMapping("/")
    public String trangChu() {
        return "forward:/index.html";
    }

    @GetMapping("/danh-muc")
    public String danhMuc() {
        return "forward:/danhmuc.html";
    }
    @GetMapping("/tour-hanh-trinh")
    public String tourHanhTrinh() {
        return "forward:/tour.html";
    }
    @GetMapping("/tour-diem-dung")
    public String tourDiemDung() {
        return "forward:/tour-diem-dung.html";
    }
    @GetMapping("/tour-phuong-tien")
    public String tourPhuongTien() {
        return "forward:/tour-phuong-tien.html";
    }
    @GetMapping("/tour-diem-tham-quan")
    public String tourDiemThamQuan() {
        return "forward:/tour-diem-tham-quan.html";
    }
    @GetMapping("/lich-chuyen-le")
    public String lichChuyenLe() {
        return "forward:/lich-chuyen-le.html";
    }
    @GetMapping("/dang-ky-le")
    public String dangKyLe() {
        return "forward:/dang-ky-le.html";
    }
    @GetMapping("/dang-ky-doan")
    public String dangKyDoan() {
        return "forward:/dang-ky-doan.html";
    }
    @GetMapping("/phan-cong")
    public String phanCong() {
        return "forward:/phan-cong.html";
    }
    @GetMapping("/ket-thuc-tour")
    public String ketThucTour() {
        return "forward:/ket-thuc-tour.html";
    }
    @GetMapping("/khao-sat-khach-hang")
    public String khaoSatKhachHang() {
        return "forward:/khao-sat-khach-hang.html";
    }
    @GetMapping("/luong-huong-dan-vien")
    public String luongHuongDanVien() {
        return "forward:/luong-huong-dan-vien.html";
    }
    @GetMapping("/luong-thong-ke-tong-hop")
    public String luongThongKeTongHop() {
        return "forward:/luong-thong-ke-tong-hop.html";
    }
}