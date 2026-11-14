package com.example.ThamVanChinhSach.domain;

/**
 * Ngoại lệ nghiệp vụ của tầng miền.
 * Dùng khi một đối tượng miền từ chối hành vi vì vi phạm quy tắc nghiệp vụ,
 * thay vì để lỗi rơi xuống tầng CSDL.
 */
public class ViPhamNghiepVuException extends RuntimeException {

    public ViPhamNghiepVuException(String thongDiep) {
        super(thongDiep);
    }
}
