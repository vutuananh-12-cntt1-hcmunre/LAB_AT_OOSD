package com.example.ThamVanChinhSach.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Unit test cho máy trạng thái của ý kiến: AI không ghi đè nhóm chính thức,
 * rà soát bắt buộc có người và thời điểm, phản hồi chỉ nhận 3 kết quả hợp lệ.
 */
class YKienTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2026, 1, 1, 8, 0);

    private YKien yKienMoi() {
        return new YKien(11, 3, 8, "Nội dung hợp lệ", NOW, "Mới tiếp nhận", null, null, null, null);
    }

    @Test
    void constructor_rejectsInvalidTopicSenderAndContent() {
        assertThatThrownBy(() -> new YKien(1, 0, 8, "nội dung", NOW, "Mới tiếp nhận", null, null, null, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> new YKien(1, 3, 0, "nội dung", NOW, "Mới tiếp nhận", null, null, null, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> new YKien(1, 3, 8, "   ", NOW, "Mới tiếp nhận", null, null, null, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
    }

    @Test
    void constructor_rejectsUnknownStatus() {
        assertThatThrownBy(() -> new YKien(1, 3, 8, "nội dung", NOW, "Trạng thái lạ", null, null, null, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
    }

    @Test
    void ghiNhanDeXuatNhom_recordsSuggestionAndMovesToPendingReview() {
        YKien yKien = yKienMoi();
        yKien.ghiNhanDeXuatNhom(3);
        assertThat(yKien.getMaNhomAI()).isEqualTo(3);
        assertThat(yKien.getTrangThai()).isEqualTo(TrangThaiYKien.CHO_RA_SOAT);
        assertThat(yKien.getMaNhomChinhThuc()).isNull(); // AI không chốt nhóm chính thức
    }

    @Test
    void ghiNhanDeXuatNhom_refusesToOverwriteOfficialGroup() {
        YKien yKien = yKienMoi();
        yKien.xacNhanRaSoat(5, 9, NOW); // đã chốt nhóm chính thức
        assertThatThrownBy(() -> yKien.ghiNhanDeXuatNhom(3))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThat(yKien.getMaNhomChinhThuc()).isEqualTo(5); // không bị ghi đè
    }

    @Test
    void xacNhanRaSoat_recordsReviewerAndTimeAndMarksReviewed() {
        YKien yKien = yKienMoi();
        yKien.xacNhanRaSoat(5, 9, NOW);
        assertThat(yKien.getMaNhomChinhThuc()).isEqualTo(5);
        assertThat(yKien.getNguoiRaSoat()).isEqualTo(9);
        assertThat(yKien.getThoiDiemRaSoat()).isEqualTo(NOW);
        assertThat(yKien.getTrangThai()).isEqualTo(TrangThaiYKien.DA_RA_SOAT);
        assertThat(yKien.daRaSoat()).isTrue();
    }

    @Test
    void xacNhanRaSoat_requiresValidGroupReviewerAndTime() {
        YKien yKien = yKienMoi();
        assertThatThrownBy(() -> yKien.xacNhanRaSoat(0, 9, NOW)).isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> yKien.xacNhanRaSoat(5, 0, NOW)).isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> yKien.xacNhanRaSoat(5, 9, null)).isInstanceOf(ViPhamNghiepVuException.class);
    }

    @Test
    void kiemTraPhanHoi_acceptsOnlyThreeResultsWithExplanation() {
        YKien yKien = yKienMoi();
        assertThatCode(() -> yKien.kiemTraPhanHoi("Tiếp thu", "Giải thích")).doesNotThrowAnyException();
        assertThatCode(() -> yKien.kiemTraPhanHoi("Tiếp thu một phần", "Giải thích")).doesNotThrowAnyException();
        assertThatCode(() -> yKien.kiemTraPhanHoi("Không tiếp thu", "Giải thích")).doesNotThrowAnyException();
    }

    @Test
    void kiemTraPhanHoi_rejectsInvalidResultOrMissingExplanation() {
        YKien yKien = yKienMoi();
        assertThatThrownBy(() -> yKien.kiemTraPhanHoi("Sai", "Giải thích"))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> yKien.kiemTraPhanHoi("Tiếp thu", "   "))
                .isInstanceOf(ViPhamNghiepVuException.class);
    }

    @Test
    void daPhanHoi_reflectsRespondedStatus() {
        YKien responded = new YKien(1, 3, 8, "nội dung", NOW, "Đã phản hồi", null, null, null, null);
        assertThat(responded.daPhanHoi()).isTrue();
        assertThat(yKienMoi().daPhanHoi()).isFalse();
    }
}
