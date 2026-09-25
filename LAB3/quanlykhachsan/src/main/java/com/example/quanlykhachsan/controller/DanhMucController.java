package com.example.quanlykhachsan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DanhMucController {

    @GetMapping("/danh-muc")
    public String danhMuc() {
        return "danh-muc";
    }
}