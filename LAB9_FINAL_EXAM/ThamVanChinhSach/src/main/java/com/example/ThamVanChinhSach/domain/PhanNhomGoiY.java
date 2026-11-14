package com.example.ThamVanChinhSach.domain;

import java.util.List;

/**
 * Kết quả gợi ý phân nhóm. Chỉ là đề xuất, không phải quyết định phân nhóm chính thức.
 */
public record PhanNhomGoiY(int maNhom,
                           String tenNhom,
                           double doTinCay,
                           List<String> tuKhoaKhop) {
}
