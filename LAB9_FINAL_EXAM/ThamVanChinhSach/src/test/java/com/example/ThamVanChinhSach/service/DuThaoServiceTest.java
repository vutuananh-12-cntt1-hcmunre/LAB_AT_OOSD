package com.example.ThamVanChinhSach.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.ThamVanChinhSach.domain.LienKetThayDoi;
import com.example.ThamVanChinhSach.dto.DuThaoRequest;
import com.example.ThamVanChinhSach.dto.LienKetThayDoiRequest;
import com.example.ThamVanChinhSach.repository.DuThaoRepository;

/**
 * P1 — kiểm thử tầng Service của dự thảo: validation được ủy quyền cho
 * đối tượng miền (DuThao/LienKetThayDoi) và lỗi ràng buộc CSDL (UNIQUE/FK)
 * phải được chuyển thành thông điệp nghiệp vụ, không ném ra ngoài.
 * Không dùng Mockito: repository được thay bằng lớp con ghi nhận lời gọi.
 */
class DuThaoServiceTest {

    private final FakeDuThaoRepository repository = new FakeDuThaoRepository();
    private final DuThaoService service = new DuThaoService(repository);

    // ---------------------------------------------------------------- tạo dự thảo

    @Test
    void taoDuThao_rejectsBlankName_withoutCallingRepository() {
        DuThaoRequest request = validRequest();
        request.setTenDuThao("   ");

        String result = service.taoDuThao(request, 2);

        assertThat(result).isEqualTo("Lỗi: Tên dự thảo không được để trống.");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoDuThao_rejectsInvalidTopic_withoutCallingRepository() {
        DuThaoRequest request = validRequest();
        request.setMaChuDe(0);

        String result = service.taoDuThao(request, 2);

        assertThat(result).isEqualTo("Lỗi: Dự thảo phải thuộc một chủ đề tham vấn.");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoDuThao_rejectsBlankVersion_withoutCallingRepository() {
        DuThaoRequest request = validRequest();
        request.setPhienBan("");

        String result = service.taoDuThao(request, 2);

        assertThat(result).isEqualTo("Lỗi: Phiên bản dự thảo không được để trống.");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoDuThao_rejectsBlankContent_withoutCallingRepository() {
        DuThaoRequest request = validRequest();
        request.setNoiDung(null);

        String result = service.taoDuThao(request, 2);

        assertThat(result).isEqualTo("Lỗi: Nội dung dự thảo không được để trống.");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoDuThao_rejectsMissingUpdater_withoutCallingRepository() {
        String result = service.taoDuThao(validRequest(), 0);

        assertThat(result).isEqualTo("Lỗi: Dự thảo phải có người cập nhật hợp lệ.");
        assertThat(repository.taoCalls).isZero();
    }

    @Test
    void taoDuThao_persistsOnlyAfterValidation() {
        DuThaoRequest request = validRequest();

        String result = service.taoDuThao(request, 2);

        assertThat(result).isEqualTo("Tạo dự thảo thành công. Mã dự thảo: 99");
        assertThat(repository.taoCalls).isEqualTo(1);
        assertThat(repository.lastRequest).isSameAs(request);
        assertThat(repository.lastNguoiCapNhat).isEqualTo(2);
    }

    @Test
    void taoDuThao_mapsForeignKeyFailureToBusinessMessage() {
        repository.taoError = new RuntimeException("FK_DU_THAO_CHU_DE");

        String result = service.taoDuThao(validRequest(), 2);

        assertThat(result).startsWith("Lỗi tạo dự thảo: ").contains("FK_DU_THAO_CHU_DE");
    }

    // ------------------------------------------------------- liên kết thay đổi

    @Test
    void taoLienKet_rejectsBlankChangeDescription_withoutCallingRepository() {
        LienKetThayDoiRequest request = validLink();
        request.setNoiDungThayDoi("  ");

        String result = service.taoLienKetThayDoi(request);

        assertThat(result).isEqualTo(
                "Lỗi liên kết thay đổi: Liên kết thay đổi phải mô tả nội dung thay đổi để bảo đảm truy vết.");
        assertThat(repository.linkCalls).isZero();
    }

    @Test
    void taoLienKet_rejectsInvalidOpinion_withoutCallingRepository() {
        LienKetThayDoiRequest request = validLink();
        request.setMaYKien(0);

        String result = service.taoLienKetThayDoi(request);

        assertThat(result).isEqualTo(
                "Lỗi liên kết thay đổi: Liên kết phải trỏ tới một ý kiến hợp lệ.");
        assertThat(repository.linkCalls).isZero();
    }

    @Test
    void taoLienKet_rejectsInvalidResponse_withoutCallingRepository() {
        LienKetThayDoiRequest request = validLink();
        request.setMaPhanHoi(0);

        String result = service.taoLienKetThayDoi(request);

        assertThat(result).isEqualTo(
                "Lỗi liên kết thay đổi: Phản hồi được liên kết không hợp lệ.");
        assertThat(repository.linkCalls).isZero();
    }

    @Test
    void taoLienKet_delegatesValidLink() {
        LienKetThayDoiRequest request = validLink();

        String result = service.taoLienKetThayDoi(request);

        assertThat(result).isEqualTo("Liên kết ý kiến với dự thảo thành công!");
        assertThat(repository.linkCalls).isEqualTo(1);
        LienKetThayDoi link = repository.lastLink;
        assertThat(link.getMaDuThaoMoi()).isEqualTo(2);
        assertThat(link.getMaYKien()).isEqualTo(10);
        assertThat(link.getMaPhanHoi()).isEqualTo(4);
        assertThat(link.getNoiDungThayDoi()).isEqualTo("Sua Dieu 5 khoan 2");
    }

    @Test
    void taoLienKet_mapsUniqueConstraintToBusinessMessage() {
        repository.linkError = new RuntimeException(
                "Vi phạm ràng buộc UQ_LKT_DUTHAO_YKIEN: ý kiến đã được liên kết với dự thảo này.");

        String result = service.taoLienKetThayDoi(validLink());

        assertThat(result).startsWith("Lỗi liên kết thay đổi: ").contains("UQ_LKT_DUTHAO_YKIEN");
    }

    // ---------------------------------------------------------------- công bố

    @Test
    void congBo_mapsMissingDraftToBusinessMessage() {
        repository.congBoError = new RuntimeException("Không tìm thấy dự thảo ID: 21");

        String result = service.congBoDuThao(21);

        assertThat(result).isEqualTo("Lỗi công bố dự thảo: Không tìm thấy dự thảo ID: 21");
        assertThat(repository.congBoCalls).isEqualTo(1);
    }

    @Test
    void congBo_successMessage() {
        String result = service.congBoDuThao(21);

        assertThat(result).isEqualTo("Công bố dự thảo thành công!");
        assertThat(repository.congBoCalls).isEqualTo(1);
    }

    // ------------------------------------------------------------------ helpers

    private DuThaoRequest validRequest() {
        DuThaoRequest request = new DuThaoRequest();
        request.setMaChuDe(1);
        request.setTenDuThao("Dự thảo kiểm thử");
        request.setPhienBan("2.0");
        request.setNoiDung("Nội dung dự thảo kiểm thử");
        return request;
    }

    private LienKetThayDoiRequest validLink() {
        LienKetThayDoiRequest request = new LienKetThayDoiRequest();
        request.setMaDuThaoMoi(2);
        request.setMaYKien(10);
        request.setMaPhanHoi(4);
        request.setNoiDungThayDoi("Sua Dieu 5 khoan 2");
        return request;
    }

    private static class FakeDuThaoRepository extends DuThaoRepository {
        int taoCalls;
        int linkCalls;
        int congBoCalls;
        DuThaoRequest lastRequest;
        int lastNguoiCapNhat;
        LienKetThayDoi lastLink;
        RuntimeException taoError;
        RuntimeException linkError;
        RuntimeException congBoError;

        FakeDuThaoRepository() {
            super(null);
        }

        @Override
        public Integer taoDuThao(DuThaoRequest request, int nguoiCapNhat) {
            taoCalls++;
            lastRequest = request;
            lastNguoiCapNhat = nguoiCapNhat;
            if (taoError != null) {
                throw taoError;
            }
            return 99;
        }

        @Override
        public void taoLienKetThayDoi(LienKetThayDoi lienKet) {
            linkCalls++;
            lastLink = lienKet;
            if (linkError != null) {
                throw linkError;
            }
        }

        @Override
        public void congBoDuThao(int maDuThao) {
            congBoCalls++;
            if (congBoError != null) {
                throw congBoError;
            }
        }
    }
}
