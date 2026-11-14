package com.example.ThamVanChinhSach.dto;

public class LoginRequest {
    private String email;
    private String matKhau;

    public String getEmail() {
        return email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
