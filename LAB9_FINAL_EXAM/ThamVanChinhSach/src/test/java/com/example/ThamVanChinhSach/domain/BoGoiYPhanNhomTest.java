package com.example.ThamVanChinhSach.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit test cho thuật toán đề xuất nhóm (BR09/BR10): kết quả chỉ là gợi ý,
 * không khớp từ khoá thì trả về null, không bao giờ tự gán bừa.
 */
class BoGoiYPhanNhomTest {

    private final BoGoiYPhanNhom boGoiY = new BoGoiYPhanNhom();

    private NhomYKien nhom(int maNhom, String tenNhom, String moTa) {
        return new NhomYKien(maNhom, 1, tenNhom, moTa);
    }

    @Test
    void goiY_rejectsBlankContent() {
        assertThatThrownBy(() -> boGoiY.goiY(null, List.of(nhom(1, "Giao thông", ""))))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> boGoiY.goiY("   ", List.of(nhom(1, "Giao thông", ""))))
                .isInstanceOf(ViPhamNghiepVuException.class);
    }

    @Test
    void goiY_returnsNullWhenNoGroups() {
        assertThat(boGoiY.goiY("giao thông", List.of())).isNull();
        assertThat(boGoiY.goiY("giao thông", null)).isNull();
    }

    @Test
    void goiY_returnsNullWhenNoKeywordMatches() {
        assertThat(boGoiY.goiY("nông nghiệp", List.of(nhom(1, "Giao thông", "đường bộ")))).isNull();
    }

    @Test
    void goiY_weighsTitleMatchAboveDescriptionMatch() {
        List<NhomYKien> cacNhom = List.of(
                nhom(1, "Giao thông", ""),
                nhom(2, "Xây dựng", "các vấn đề giao thông"));
        PhanNhomGoiY goiY = boGoiY.goiY("giao thông", cacNhom);
        assertThat(goiY).isNotNull();
        assertThat(goiY.maNhom()).isEqualTo(1); // tên nhóm (trọng số 2) thắng mô tả (trọng số 1)
    }

    @Test
    void goiY_normalizesDiacriticsCaseAndIgnoresNumbersAndStopWords() {
        PhanNhomGoiY goiY = boGoiY.goiY("AN TOÀN 2026 và các", List.of(nhom(1, "An toàn", "")));
        assertThat(goiY).isNotNull();
        assertThat(goiY.doTinCay()).isEqualTo(1.0);
        assertThat(goiY.tuKhoaKhop()).contains("an", "toan");
    }

    @Test
    void goiY_capsConfidenceAtOne() {
        PhanNhomGoiY goiY = boGoiY.goiY("giao thông", List.of(nhom(1, "Giao thông", "")));
        assertThat(goiY).isNotNull();
        assertThat(goiY.doTinCay()).isLessThanOrEqualTo(1.0);
        assertThat(goiY.doTinCay()).isEqualTo(1.0); // mọi từ khoá khớp tên nhóm
    }

    @Test
    void goiY_returnsMatchedKeywordsForHumanVerification() {
        PhanNhomGoiY goiY = boGoiY.goiY("giao thông", List.of(nhom(1, "Giao thông", "")));
        assertThat(goiY).isNotNull();
        assertThat(goiY.tuKhoaKhop()).containsExactlyInAnyOrder("giao", "thong");
    }
}
