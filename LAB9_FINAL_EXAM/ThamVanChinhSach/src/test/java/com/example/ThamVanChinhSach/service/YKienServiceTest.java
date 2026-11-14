package com.example.ThamVanChinhSach.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ThamVanChinhSach.domain.YKien;
import com.example.ThamVanChinhSach.dto.RaSoatRequest;
import com.example.ThamVanChinhSach.dto.YKienCreateRequest;
import com.example.ThamVanChinhSach.repository.TruyVetRepository;
import com.example.ThamVanChinhSach.repository.YKienRepository;

/** Unit tests cho luồng tiếp nhận, rà soát và phản hồi ý kiến. */
@ExtendWith(MockitoExtension.class)
class YKienServiceTest {

    @Mock
    private YKienRepository yKienRepository;

    @Mock
    private TruyVetRepository truyVetRepository;

    private YKienService service;

    @BeforeEach
    void setUp() {
        service = new YKienService(yKienRepository, truyVetRepository);
    }

    @Test
    void guiYKien_delegatesAuthenticatedSenderAndReturnsNewId() {
        YKienCreateRequest request = new YKienCreateRequest();
        request.setMaChuDe(3);
        request.setNoiDung("Nội dung góp ý");
        when(yKienRepository.guiYKien(3, 8, "Nội dung góp ý")).thenReturn(42);

        String result = service.guiYKien(request, 8);

        assertThat(result).isEqualTo("Gửi ý kiến thành công. Mã ý kiến mới: 42");
        verify(yKienRepository).guiYKien(3, 8, "Nội dung góp ý");
    }

    @Test
    void guiYKien_convertsRepositoryFailureToFailureMessage() {
        YKienCreateRequest request = new YKienCreateRequest();
        request.setMaChuDe(3);
        request.setNoiDung("Nội dung góp ý");
        when(yKienRepository.guiYKien(3, 8, "Nội dung góp ý"))
                .thenThrow(new RuntimeException("Chủ đề đã đóng"));

        String result = service.guiYKien(request, 8);

        assertThat(result).startsWith("Gửi ý kiến thất bại:");
        assertThat(result).contains("Chủ đề đã đóng");
    }

    @Test
    void raSoatYKien_recordsOfficialGroupAndReviewer() {
        RaSoatRequest request = new RaSoatRequest();
        request.setMaNhomChinhThuc(5);
        when(yKienRepository.timTheoMa(11)).thenReturn(validOpinion(11));

        String result = service.raSoatYKien(11, request, 9);

        assertThat(result).isEqualTo("Cán bộ rà soát phân nhóm thành công cho ý kiến ID: 11");
        verify(yKienRepository).raSoatPhanNhom(11, 5, 9);
    }

    @Test
    void raSoatYKien_rejectsMissingReviewerBeforeWriting() {
        RaSoatRequest request = new RaSoatRequest();
        request.setMaNhomChinhThuc(5);
        when(yKienRepository.timTheoMa(11)).thenReturn(validOpinion(11));

        String result = service.raSoatYKien(11, request, 0);

        assertThat(result).startsWith("Lỗi rà soát:");
        assertThat(result).contains("bắt buộc phải do người rà soát xác nhận");
    }

    @Test
    void raSoatYKien_returnsNotFoundWithoutWriting() {
        RaSoatRequest request = new RaSoatRequest();
        request.setMaNhomChinhThuc(5);
        when(yKienRepository.timTheoMa(11)).thenReturn(null);

        String result = service.raSoatYKien(11, request, 9);

        assertThat(result).isEqualTo("Lỗi rà soát: Không tìm thấy ý kiến có ID: 11");
    }

    @Test
    void raSoatYKien_keepsFailureVisibleWhenDatabaseRejectsReview() {
        RaSoatRequest request = new RaSoatRequest();
        request.setMaNhomChinhThuc(5);
        when(yKienRepository.timTheoMa(11)).thenReturn(validOpinion(11));
        doThrow(new RuntimeException("Nhóm không thuộc chủ đề"))
                .when(yKienRepository).raSoatPhanNhom(11, 5, 9);

        String result = service.raSoatYKien(11, request, 9);

        assertThat(result).isEqualTo("Lỗi rà soát: Nhóm không thuộc chủ đề");
    }

    @Test
    void phanHoiYKien_delegatesDecisionExplanationAndOfficer() {
        when(yKienRepository.timTheoMa(11)).thenReturn(validOpinion(11));

        String result = service.phanHoiYKien(11, "Tiếp thu một phần", "Đã điều chỉnh mục 2", 9);

        assertThat(result).isEqualTo("Phản hồi ý kiến thành công cho ý kiến ID: 11");
        verify(yKienRepository).phanHoiYKien(11, "Tiếp thu một phần", "Đã điều chỉnh mục 2", 9);
    }

    @Test
    void phanHoiYKien_rejectsInvalidDecisionBeforeWriting() {
        when(yKienRepository.timTheoMa(11)).thenReturn(validOpinion(11));

        String result = service.phanHoiYKien(11, "Sai", "Giải thích", 9);

        assertThat(result).startsWith("Lỗi phản hồi ý kiến:");
        assertThat(result).contains("Kết quả xử lý chỉ nhận");
    }

    @Test
    void phanHoiYKien_returnsNotFoundWithoutWriting() {
        when(yKienRepository.timTheoMa(11)).thenReturn(null);

        String result = service.phanHoiYKien(11, "Tiếp thu", "Giải thích", 9);

        assertThat(result).isEqualTo("Lỗi phản hồi ý kiến: Không tìm thấy ý kiến có ID: 11");
    }

    @Test
    void phanHoiYKien_returnsFailureWhenRepositoryRejectsInput() {
        when(yKienRepository.timTheoMa(11)).thenReturn(validOpinion(11));
        doThrow(new RuntimeException("Không thể ghi phản hồi"))
                .when(yKienRepository).phanHoiYKien(11, "Tiếp thu", "Giải thích", 9);

        String result = service.phanHoiYKien(11, "Tiếp thu", "Giải thích", 9);

        assertThat(result).isEqualTo("Lỗi phản hồi ý kiến: Không thể ghi phản hồi");
    }

    @Test
    void listMethods_delegateToRepository() {
        when(yKienRepository.layDanhSachYKien()).thenReturn(List.of());
        when(yKienRepository.layDanhSachYKienCaNhan(8)).thenReturn(List.of());

        assertThat(service.layDanhSachYKien()).isEmpty();
        assertThat(service.layDanhSachYKienCaNhan(8)).isEmpty();
        verify(yKienRepository).layDanhSachYKien();
        verify(yKienRepository).layDanhSachYKienCaNhan(8);
    }

    private YKien validOpinion(int maYKien) {
        return new YKien(
                maYKien,
                3,
                8,
                "Nội dung hợp lệ",
                LocalDateTime.of(2026, 1, 1, 8, 0),
                "Mới tiếp nhận",
                null,
                null,
                null,
                null);
    }
}
