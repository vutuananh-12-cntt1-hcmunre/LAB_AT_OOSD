package com.example.ThamVanChinhSach.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.ThamVanChinhSach.dto.CongBoKetQuaRequest;
import com.example.ThamVanChinhSach.dto.CongBoKetQuaResponse;
import com.example.ThamVanChinhSach.repository.CongBoRepository;

/**
 * P1 — kiểm thử tầng Service công bố kết quả: validation nội dung và
 * việc chuyển lỗi trigger TR_CONG_BO_KIEM_TRA_CHU_DE thành thông điệp 400
 * thay vì để lỗi CSDL rơi ra ngoài.
 * Không dùng Mockito.
 */
class CongBoServiceTest {

    private final FakeCongBoRepository repository = new FakeCongBoRepository();
    private final CongBoService service = new CongBoService(repository);

    @Test
    void taoCongBo_rejectsBlankContent_withoutCallingRepository() {
        CongBoKetQuaRequest request = validRequest();
        request.setNoiDungCongBo("   ");

        String result = service.taoCongBo(request, 2);

        assertThat(result).isEqualTo("Lỗi: Nội dung công bố không được để trống!");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoCongBo_rejectsNullContent_withoutCallingRepository() {
        CongBoKetQuaRequest request = validRequest();
        request.setNoiDungCongBo(null);

        String result = service.taoCongBo(request, 2);

        assertThat(result).isEqualTo("Lỗi: Nội dung công bố không được để trống!");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoCongBo_persistsValidRequest() {
        CongBoKetQuaRequest request = validRequest();

        String result = service.taoCongBo(request, 2);

        assertThat(result).isEqualTo("Công bố kết quả thành công. Mã công bố: 55");
        assertThat(repository.taoCalls).isEqualTo(1);
        assertThat(repository.lastRequest).isSameAs(request);
        assertThat(repository.lastNguoiCongBo).isEqualTo(2);
    }

    @Test
    void taoCongBo_mapsTopicMismatchTriggerToBusinessMessage() {
        repository.taoError = new RuntimeException(
                "Phiên bản dự thảo được công bố phải thuộc đúng chủ đề tham vấn.");

        String result = service.taoCongBo(validRequest(), 2);

        assertThat(result)
                .startsWith("Lỗi công bố kết quả: ")
                .contains("thuộc đúng chủ đề tham vấn");
    }

    @Test
    void layTatCa_delegatesToRepository() {
        CongBoKetQuaResponse response = new CongBoKetQuaResponse();
        response.setMaCongBo(1);
        repository.tatCa = List.of(response);

        assertThat(service.layTatCa()).containsExactly(response);
    }

    @Test
    void layDanhSachTheoChuDe_delegatesToRepository() {
        CongBoKetQuaResponse response = new CongBoKetQuaResponse();
        response.setMaCongBo(2);
        repository.theoChuDe = List.of(response);

        assertThat(service.layDanhSachTheoChuDe(3)).containsExactly(response);
        assertThat(repository.lastMaChuDe).isEqualTo(3);
    }

    private CongBoKetQuaRequest validRequest() {
        CongBoKetQuaRequest request = new CongBoKetQuaRequest();
        request.setMaChuDe(1);
        request.setMaDuThao(2);
        request.setNoiDungCongBo("Kết quả tham vấn đã được công bố.");
        return request;
    }

    private static class FakeCongBoRepository extends CongBoRepository {
        int taoCalls;
        int lastMaChuDe;
        CongBoKetQuaRequest lastRequest;
        int lastNguoiCongBo;
        List<CongBoKetQuaResponse> tatCa = List.of();
        List<CongBoKetQuaResponse> theoChuDe = List.of();
        RuntimeException taoError;

        FakeCongBoRepository() {
            super(null);
        }

        @Override
        public Integer taoCongBo(CongBoKetQuaRequest request, int nguoiCongBo) {
            taoCalls++;
            lastRequest = request;
            lastNguoiCongBo = nguoiCongBo;
            if (taoError != null) {
                throw taoError;
            }
            return 55;
        }

        @Override
        public List<CongBoKetQuaResponse> layDanhSachTheoChuDe(int maChuDe) {
            lastMaChuDe = maChuDe;
            return theoChuDe;
        }

        @Override
        public List<CongBoKetQuaResponse> layTatCa() {
            return tatCa;
        }
    }
}
