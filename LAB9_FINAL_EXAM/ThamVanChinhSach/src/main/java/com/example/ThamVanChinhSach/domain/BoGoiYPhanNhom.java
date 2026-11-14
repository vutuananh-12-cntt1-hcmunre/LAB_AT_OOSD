package com.example.ThamVanChinhSach.domain;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Bộ gợi ý phân nhóm dựa trên trùng khớp từ khoá (heuristic minh bạch, chạy nội bộ).
 *
 * Nguyên tắc thiết kế:
 * - Chỉ ĐỀ XUẤT nhóm, không chốt nhóm và không loại bỏ ý kiến nào.
 * - Kết quả trả về kèm từ khoá khớp để cán bộ rà soát kiểm chứng được.
 * - Nếu không đủ căn cứ thì trả về null; hệ thống không tự gán bừa.
 */
public class BoGoiYPhanNhom {

    private static final Set<String> TU_DUNG = new HashSet<>(Arrays.asList(
            "va", "la", "cho", "ve", "cac", "nhung", "mot", "voi", "trong", "tren",
            "khi", "duoc", "se", "co", "khong", "nay", "do", "thi", "ra", "vao",
            "tu", "den", "theo", "cung", "nhu", "de", "hoac", "neu", "vi", "gi",
            "nao", "toi", "ban", "chung", "minh", "nguoi", "viec", "cai", "chiec",
            "rat", "hon", "nhat", "nhom", "cua", "bang", "tai", "qua", "roi",
            "dang", "bi", "boi", "nhieu", "cung", "moi", "hay", "vay"));

    /**
     * Gợi ý nhóm phù hợp nhất cho nội dung ý kiến.
     *
     * @return kết quả gợi ý, hoặc null nếu không có nhóm nào trùng từ khoá.
     */
    public PhanNhomGoiY goiY(String noiDung, List<NhomYKien> danhSachNhom) {
        if (noiDung == null || noiDung.trim().isEmpty()) {
            throw new ViPhamNghiepVuException("Không thể gợi ý nhóm cho ý kiến rỗng.");
        }
        if (danhSachNhom == null || danhSachNhom.isEmpty()) {
            return null;
        }

        Set<String> tuKhoaYKien = tachTuKhoa(noiDung);
        if (tuKhoaYKien.isEmpty()) {
            return null;
        }

        PhanNhomGoiY totNhat = null;
        double diemTotNhat = 0.0;

        for (NhomYKien nhom : danhSachNhom) {
            Set<String> tuKhoaTenNhom = tachTuKhoa(nhom.getTenNhom());
            Set<String> tuKhoaMoTa = tachTuKhoa(nhom.getMoTa());

            double diem = 0.0;
            List<String> tuKhoaKhop = new ArrayList<>();
            for (String tuKhoa : tuKhoaYKien) {
                if (tuKhoaTenNhom.contains(tuKhoa)) {
                    diem += 2.0;
                    tuKhoaKhop.add(tuKhoa);
                } else if (tuKhoaMoTa.contains(tuKhoa)) {
                    diem += 1.0;
                    tuKhoaKhop.add(tuKhoa);
                }
            }

            if (diem <= 0.0) {
                continue;
            }

            double doTinCay = Math.min(1.0, diem / (tuKhoaYKien.size() * 2.0));
            if (diem > diemTotNhat) {
                diemTotNhat = diem;
                totNhat = new PhanNhomGoiY(nhom.getMaNhom(), nhom.getTenNhom(), doTinCay, List.copyOf(tuKhoaKhop));
            }
        }

        return totNhat;
    }

    /**
     * Tách từ khoá: bỏ dấu tiếng Việt, chữ thường, bỏ từ dừng, bỏ số và từ quá ngắn.
     */
    private Set<String> tachTuKhoa(String vanBan) {
        if (vanBan == null || vanBan.trim().isEmpty()) {
            return Set.of();
        }
        String khongDau = Normalizer.normalize(vanBan, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replace('đ', 'd')
                .replace('Đ', 'D')
                .toLowerCase(Locale.ROOT);

        Set<String> ketQua = new LinkedHashSet<>();
        for (String tu : khongDau.split("[^a-z0-9]+")) {
            if (tu.length() < 2) {
                continue;
            }
            if (tu.chars().allMatch(Character::isDigit)) {
                continue;
            }
            if (TU_DUNG.contains(tu)) {
                continue;
            }
            ketQua.add(tu);
        }
        return ketQua;
    }
}
