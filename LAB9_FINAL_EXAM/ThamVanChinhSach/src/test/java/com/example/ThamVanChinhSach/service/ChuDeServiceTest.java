package com.example.ThamVanChinhSach.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ThamVanChinhSach.dto.CapNhatTrangThaiRequest;
import com.example.ThamVanChinhSach.dto.ChuDeRequest;
import com.example.ThamVanChinhSach.dto.ChuDeUpdateRequest;
import com.example.ThamVanChinhSach.repository.ChuDeRepository;

/**
 * Unit tests cho các business rule được kiểm tra trước khi gọi repository.
 * Không cần SQL Server: repository được mock để test trách nhiệm của service.
 */
@ExtendWith(MockitoExtension.class)
class ChuDeServiceTest {

    @Mock
    private ChuDeRepository chuDeRepository;

    private ChuDeService service;

    @BeforeEach
    void setUp() {
        service = new ChuDeService(chuDeRepository);
    }

    @Test
    void taoChuDe_rejectsBlankTitle_withoutCallingRepository() {
        ChuDeRequest request = validCreateRequest();
        request.setTieuDe("   ");

        String result = service.taoChuDe(request, 10);

        assertThat(result).isEqualTo("Lỗi: Tiêu đề không được để trống!");
        verifyNoInteractions(chuDeRepository);
    }

    @Test
    void taoChuDe_rejectsMissingTime_withoutCallingRepository() {
        ChuDeRequest request = validCreateRequest();
        request.setThoiGianKetThuc(null);

        String result = service.taoChuDe(request, 10);

        assertThat(result).isEqualTo("Lỗi: Vui lòng nhập đầy đủ thời gian bắt đầu và thời gian kết thúc!");
        verifyNoInteractions(chuDeRepository);
    }

    @Test
    void taoChuDe_rejectsEndAtOrBeforeStart_withoutCallingRepository() {
        ChuDeRequest request = validCreateRequest();
        request.setThoiGianKetThuc(request.getThoiGianBatDau());

        String result = service.taoChuDe(request, 10);

        assertThat(result).isEqualTo("Lỗi: Ngày giờ kết thúc phải sau ngày giờ bắt đầu!");
        verifyNoInteractions(chuDeRepository);
    }

    @Test
    void taoChuDe_delegatesOnlyAfterValidation() {
        ChuDeRequest request = validCreateRequest();

        String result = service.taoChuDe(request, 10);

        assertThat(result).isEqualTo("Tạo chủ đề tham vấn thành công!");
        verify(chuDeRepository).taoChuDe(request, 10);
    }

    @Test
    void taoChuDe_mapsDatabaseTimeConstraintToBusinessMessage() {
        ChuDeRequest request = validCreateRequest();
        doThrow(new RuntimeException("CK_CHU_DE_THOI_GIAN"))
                .when(chuDeRepository).taoChuDe(any(ChuDeRequest.class), any(Integer.class));

        String result = service.taoChuDe(request, 10);

        assertThat(result).isEqualTo("Lỗi: Ngày giờ kết thúc phải sau ngày giờ bắt đầu!");
    }

    @Test
    void capNhatChuDe_rejectsBlankTitle_withoutCallingRepository() {
        ChuDeUpdateRequest request = validUpdateRequest();
        request.setTieuDe("");

        String result = service.capNhatChuDe(7, request);

        assertThat(result).isEqualTo("Lỗi: Tiêu đề không được để trống!");
        verifyNoInteractions(chuDeRepository);
    }

    @Test
    void capNhatChuDe_rejectsInvalidTimeRange_withoutCallingRepository() {
        ChuDeUpdateRequest request = validUpdateRequest();
        request.setThoiGianKetThuc(request.getThoiGianBatDau().minusMinutes(1));

        String result = service.capNhatChuDe(7, request);

        assertThat(result).isEqualTo("Lỗi: Ngày giờ kết thúc phải sau ngày giờ bắt đầu!");
        verifyNoInteractions(chuDeRepository);
    }

    @Test
    void capNhatTrangThai_rejectsUnknownStatus_withoutCallingRepository() {
        CapNhatTrangThaiRequest request = new CapNhatTrangThaiRequest();
        request.setTrangThai("Đang xử lý");

        String result = service.capNhatTrangThai(7, request);

        assertThat(result).startsWith("Lỗi: Trạng thái không hợp lệ!");
        verify(chuDeRepository, never()).capNhatTrangThai(any(Integer.class), any(String.class));
    }

    @Test
    void capNhatTrangThai_acceptsOnlyDefinedStatuses() {
        CapNhatTrangThaiRequest request = new CapNhatTrangThaiRequest();
        request.setTrangThai("Đang mở");

        String result = service.capNhatTrangThai(7, request);

        assertThat(result).isEqualTo("Cập nhật trạng thái chủ đề thành công!");
        verify(chuDeRepository).capNhatTrangThai(7, "Đang mở");
    }

    private ChuDeRequest validCreateRequest() {
        ChuDeRequest request = new ChuDeRequest();
        request.setMaCoQuan(1);
        request.setTieuDe("Chủ đề kiểm thử");
        request.setNoiDungMoTa("Mô tả");
        request.setThoiGianBatDau(LocalDateTime.of(2026, 1, 1, 8, 0));
        request.setThoiGianKetThuc(LocalDateTime.of(2026, 1, 2, 8, 0));
        return request;
    }

    private ChuDeUpdateRequest validUpdateRequest() {
        ChuDeUpdateRequest request = new ChuDeUpdateRequest();
        request.setTieuDe("Chủ đề cập nhật");
        request.setNoiDungMoTa("Mô tả mới");
        request.setThoiGianBatDau(LocalDateTime.of(2026, 1, 1, 8, 0));
        request.setThoiGianKetThuc(LocalDateTime.of(2026, 1, 2, 8, 0));
        return request;
    }
}
