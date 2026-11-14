package com.example.ThamVanChinhSach.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * E2E ở lớp miền cho chuỗi truy vết (BR23):
 * Ý kiến → Phân nhóm (AI đề xuất) → Rà soát (con người) → Phản hồi
 * → Thay đổi dự thảo (phiên bản mới) → Liên kết truy vết.
 * Không cần CSDL: dùng chính các đối tượng miền để xác nhận bất biến từng mắt xích.
 */
class ChuoiTruyVetTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2026, 1, 1, 8, 0);

    @Test
    void fullTraceabilityChain_holdsEveryInvariant() {
        NhomYKien nhom = new NhomYKien(3, 1, "Giao thông", "chính sách giao thông đô thị");
        BoGoiYPhanNhom boGoiY = new BoGoiYPhanNhom();

        // 1. Tiếp nhận ý kiến
        YKien yKien = new YKien(11, 1, 8, "giao thông", NOW, "Mới tiếp nhận", null, null, null, null);
        assertThat(yKien.getTrangThai()).isEqualTo(TrangThaiYKien.MOI_TIEP_NHAN);

        // 2. Hệ thống đề xuất nhóm (AI) — chỉ là gợi ý, không chốt
        PhanNhomGoiY goiY = boGoiY.goiY(yKien.getNoiDung(), List.of(nhom));
        assertThat(goiY).isNotNull();
        yKien.ghiNhanDeXuatNhom(goiY.maNhom());
        assertThat(yKien.getMaNhomAI()).isEqualTo(3);
        assertThat(yKien.getMaNhomChinhThuc()).isNull();
        assertThat(yKien.getTrangThai()).isEqualTo(TrangThaiYKien.CHO_RA_SOAT);

        // 3. Cán bộ rà soát chốt nhóm chính thức (con người)
        yKien.xacNhanRaSoat(3, 9, NOW);
        assertThat(yKien.getMaNhomChinhThuc()).isEqualTo(3);
        assertThat(yKien.getNguoiRaSoat()).isEqualTo(9);
        assertThat(yKien.getThoiDiemRaSoat()).isEqualTo(NOW);
        assertThat(yKien.daRaSoat()).isTrue();

        // 4. Cơ quan phản hồi
        yKien.kiemTraPhanHoi("Tiếp thu", "Đã điều chỉnh mục 3");

        // 5. Cập nhật dự thảo: phiên bản mới kế thừa phiên bản trước
        DuThao banNhap = DuThao.taoMoi(1, "Dự thảo giao thông", "v1", "Nội dung gốc", 9, null, null);
        assertThat(banNhap.laPhienBanKeTiep()).isFalse();
        DuThao moi = DuThao.taoMoi(1, "Dự thảo giao thông", "v2", "Nội dung sửa", 9, 20, "Bổ sung mục 3");
        assertThat(moi.laPhienBanKeTiep()).isTrue();
        assertThat(moi.getMaDuThaoTruoc()).isEqualTo(20);

        // 6. Liên kết truy vết: ý kiến + phản hồi làm căn cứ cho phiên bản mới
        LienKetThayDoi lienKet = LienKetThayDoi.taoMoi(21, 11, 5, "Bổ sung mục 3");
        assertThat(lienKet.getMaDuThaoMoi()).isEqualTo(21);
        assertThat(lienKet.getMaYKien()).isEqualTo(11);
        assertThat(lienKet.getMaPhanHoi()).isEqualTo(5);
        assertThat(lienKet.getNoiDungThayDoi()).isEqualTo("Bổ sung mục 3");
    }

    @Test
    void draftVersioning_requiresCompleteData() {
        assertThatThrownBy(() -> DuThao.taoMoi(1, "   ", "v1", "nội dung", 9, null, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> DuThao.taoMoi(1, "Tên", "   ", "nội dung", 9, null, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> DuThao.taoMoi(1, "Tên", "v1", "   ", 9, null, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> DuThao.taoMoi(1, "Tên", "v1", "nội dung", 9, 0, null))
                .isInstanceOf(ViPhamNghiepVuException.class);
    }

    @Test
    void changeLink_requiresNewDraftOpinionAndChangeDescription() {
        assertThatThrownBy(() -> LienKetThayDoi.taoMoi(0, 11, 5, "đổi")).isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> LienKetThayDoi.taoMoi(21, 0, 5, "đổi")).isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> LienKetThayDoi.taoMoi(21, 11, 0, "đổi")).isInstanceOf(ViPhamNghiepVuException.class);
        assertThatThrownBy(() -> LienKetThayDoi.taoMoi(21, 11, 5, "   ")).isInstanceOf(ViPhamNghiepVuException.class);
    }
}
