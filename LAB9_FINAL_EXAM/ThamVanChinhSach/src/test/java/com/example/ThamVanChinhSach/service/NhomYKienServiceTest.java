package com.example.ThamVanChinhSach.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.ThamVanChinhSach.dto.NhomYKienRequest;
import com.example.ThamVanChinhSach.dto.NhomYKienResponse;
import com.example.ThamVanChinhSach.repository.NhomYKienRepository;

/**
 * P1 — kiểm thử tầng Service của nhóm ý kiến: validation tên nhóm và
 * bảo vệ xóa nhóm đang được ý kiến tham chiếu (không phá vỡ khóa ngoại/trigger).
 * Không dùng Mockito.
 */
class NhomYKienServiceTest {

    private final FakeNhomYKienRepository repository = new FakeNhomYKienRepository();
    private final NhomYKienService service = new NhomYKienService(repository);

    @Test
    void taoNhom_rejectsBlankName_withoutCallingRepository() {
        NhomYKienRequest request = request("   ");

        String result = service.taoNhom(request);

        assertThat(result).isEqualTo("Lỗi: Tên nhóm không được để trống!");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoNhom_persistsValidRequest() {
        String result = service.taoNhom(request("Nhóm giao thông"));

        assertThat(result).isEqualTo("Tạo nhóm ý kiến thành công. Mã nhóm: 7");
        assertThat(repository.taoCalls).isEqualTo(1);
        assertThat(repository.lastTaoRequest.getTenNhom()).isEqualTo("Nhóm giao thông");
    }

    @Test
    void taoNhom_mapsRepositoryFailureToBusinessMessage() {
        repository.taoError = new RuntimeException("FK_NHOM_Y_KIEN_CHU_DE");

        String result = service.taoNhom(request("Nhóm giao thông"));

        assertThat(result).startsWith("Lỗi tạo nhóm: ").contains("FK_NHOM_Y_KIEN_CHU_DE");
    }

    @Test
    void capNhatNhom_rejectsBlankName_withoutCallingRepository() {
        String result = service.capNhatNhom(5, request(""));

        assertThat(result).isEqualTo("Lỗi: Tên nhóm không được để trống!");
        assertThat(repository.capNhatCalls).isZero();
    }

    @Test
    void capNhatNhom_mapsMissingGroupToBusinessMessage() {
        repository.capNhatError = new RuntimeException("Không tìm thấy nhóm ý kiến ID: 5");

        String result = service.capNhatNhom(5, request("Nhóm mới"));

        assertThat(result).isEqualTo("Lỗi cập nhật nhóm: Không tìm thấy nhóm ý kiến ID: 5");
    }

    @Test
    void xoaNhom_refusesToDeleteGroupStillReferencedByOpinions() {
        repository.xoaError = new RuntimeException(
                "Không thể xóa nhóm vì còn 2 ý kiến đang gắn với nhóm này!");

        String result = service.xoaNhom(5);

        assertThat(result).isEqualTo(
                "Lỗi xóa nhóm: Không thể xóa nhóm vì còn 2 ý kiến đang gắn với nhóm này!");
        assertThat(repository.xoaCalls).isEqualTo(1);
    }

    @Test
    void xoaNhom_successMessage() {
        String result = service.xoaNhom(5);

        assertThat(result).isEqualTo("Xóa nhóm ý kiến thành công!");
        assertThat(repository.xoaCalls).isEqualTo(1);
    }

    @Test
    void layDanhSachTheoChuDe_delegatesToRepository() {
        NhomYKienResponse response = new NhomYKienResponse();
        response.setMaNhom(5);
        response.setMaChuDe(1);
        response.setTenNhom("Nhóm giao thông");
        repository.danhSach = List.of(response);

        List<NhomYKienResponse> result = service.layDanhSachTheoChuDe(1);

        assertThat(result).containsExactly(response);
    }

    private NhomYKienRequest request(String tenNhom) {
        NhomYKienRequest request = new NhomYKienRequest();
        request.setMaChuDe(1);
        request.setTenNhom(tenNhom);
        request.setMoTa("Mô tả nhóm");
        return request;
    }

    private static class FakeNhomYKienRepository extends NhomYKienRepository {
        int taoCalls;
        int capNhatCalls;
        int xoaCalls;
        NhomYKienRequest lastTaoRequest;
        List<NhomYKienResponse> danhSach = List.of();
        RuntimeException taoError;
        RuntimeException capNhatError;
        RuntimeException xoaError;

        FakeNhomYKienRepository() {
            super(null);
        }

        @Override
        public List<NhomYKienResponse> layDanhSachTheoChuDe(int maChuDe) {
            return danhSach;
        }

        @Override
        public Integer taoNhom(NhomYKienRequest request) {
            taoCalls++;
            lastTaoRequest = request;
            if (taoError != null) {
                throw taoError;
            }
            return 7;
        }

        @Override
        public void capNhatNhom(int maNhom, String tenNhom, String moTa) {
            capNhatCalls++;
            if (capNhatError != null) {
                throw capNhatError;
            }
        }

        @Override
        public void xoaNhom(int maNhom) {
            xoaCalls++;
            if (xoaError != null) {
                throw xoaError;
            }
        }
    }
}
