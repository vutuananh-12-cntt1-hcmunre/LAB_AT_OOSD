package com.example.ThamVanChinhSach.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.example.ThamVanChinhSach.dto.TaiLieuResponse;
import com.example.ThamVanChinhSach.repository.TaiLieuRepository;

/**
 * P1 — kiểm thử tầng Service tài liệu: chặn file rỗng/tên không hợp lệ,
 * ghi metadata đúng sau khi lưu file, và xử lý tài liệu không tồn tại.
 * Không dùng Mockito; file thử được dọn lại sau mỗi ca upload.
 */
class TaiLieuServiceTest {

    private final FakeTaiLieuRepository repository = new FakeTaiLieuRepository();
    private final TaiLieuService service = new TaiLieuService(repository);

    @Test
    void luuFile_rejectsEmptyFile_withoutPersisting() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "rong.txt", "text/plain", new byte[0]);

        String result = service.luuFileTaiLieu(10, file);

        assertThat(result).isEqualTo("Lỗi: File rỗng!");
        assertThat(repository.luuCalls).isZero();
    }

    @Test
    void luuFile_rejectsFilenameWithoutExtension_withoutPersisting() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "tailieu", "text/plain", new byte[]{1, 2, 3});

        String result = service.luuFileTaiLieu(10, file);

        assertThat(result).isEqualTo("Lỗi: Tên file không hợp lệ!");
        assertThat(repository.luuCalls).isZero();
    }

    @Test
    void luuFile_rejectsNullFilename_withoutPersisting() {
        MockMultipartFile file = new MockMultipartFile(
                "file", null, "text/plain", new byte[]{1, 2, 3});

        String result = service.luuFileTaiLieu(10, file);

        assertThat(result).isEqualTo("Lỗi: Tên file không hợp lệ!");
        assertThat(repository.luuCalls).isZero();
    }

    @Test
    void luuFile_storesFileAndMetadata() throws IOException {
        byte[] noiDung = "nội dung tài liệu".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file = new MockMultipartFile(
                "file", "ghichu.txt", "text/plain", noiDung);
        Set<String> truocKhiUpload = lietKeFileUpload();

        String result = service.luuFileTaiLieu(10, file);

        assertThat(result).isEqualTo("Upload thành công file: ghichu.txt");
        assertThat(repository.luuCalls).isEqualTo(1);
        assertThat(repository.maYKien).isEqualTo(10);
        assertThat(repository.tenTep).isEqualTo("ghichu.txt");
        assertThat(repository.loaiTep).isEqualTo("text/plain");
        assertThat(repository.kichThuoc).isEqualTo(noiDung.length);
        assertThat(repository.duongDan).startsWith("/uploads/").endsWith(".txt");

        donFileMoi(truocKhiUpload);
        assertThat(lietKeFileUpload()).containsExactlyInAnyOrderElementsOf(truocKhiUpload);
    }

    @Test
    void luuFile_mapsDatabaseRejectionToBusinessMessage_andRemovesOrphanFile() throws IOException {
        repository.luuError = new RuntimeException("FK_TAI_LIEU_Y_KIEN");
        MockMultipartFile file = new MockMultipartFile(
                "file", "hoso.pdf", "application/pdf", new byte[]{1, 2, 3});
        Set<String> truocKhiUpload = lietKeFileUpload();

        String result = service.luuFileTaiLieu(999, file);

        assertThat(result).isEqualTo("Lỗi lưu tài liệu: FK_TAI_LIEU_Y_KIEN");
        assertThat(repository.luuCalls).isEqualTo(1);
        // File vừa ghi phải bị dọn vì CSDL đã từ chối metadata
        assertThat(lietKeFileUpload()).containsExactlyInAnyOrderElementsOf(truocKhiUpload);
    }

    @Test
    void xoaTaiLieu_returnsErrorWhenNotFound_withoutDeleting() {
        repository.chiTiet = null;

        String result = service.xoaTaiLieu(3);

        assertThat(result).isEqualTo("Lỗi: Không tìm thấy tài liệu ID: 3");
        assertThat(repository.xoaCalls).isZero();
    }

    @Test
    void xoaTaiLieu_deletesMetadataWhenFound() {
        TaiLieuResponse taiLieu = new TaiLieuResponse();
        taiLieu.setMaTaiLieu(3);
        taiLieu.setDuongDanTep(null); // không có file vật lý để xóa
        repository.chiTiet = taiLieu;

        String result = service.xoaTaiLieu(3);

        assertThat(result).isEqualTo("Xóa tài liệu thành công!");
        assertThat(repository.xoaCalls).isEqualTo(1);
        assertThat(repository.lastMaTaiLieu).isEqualTo(3);
    }

    @Test
    void layTheoYKien_delegatesToRepository() {
        TaiLieuResponse taiLieu = new TaiLieuResponse();
        taiLieu.setMaTaiLieu(3);
        repository.danhSach = java.util.List.of(taiLieu);

        assertThat(service.layTheoYKien(10)).containsExactly(taiLieu);
        assertThat(repository.lastMaYKien).isEqualTo(10);
    }

    private static Path thuMucUpload() {
        return Paths.get(System.getProperty("user.dir"), "uploads");
    }

    private static Set<String> lietKeFileUpload() throws IOException {
        Path thuMuc = thuMucUpload();
        if (!Files.isDirectory(thuMuc)) {
            return Set.of();
        }
        try (Stream<Path> stream = Files.list(thuMuc)) {
            return stream.map(path -> path.getFileName().toString()).collect(Collectors.toSet());
        }
    }

    private static void donFileMoi(Set<String> truocKhiUpload) throws IOException {
        Path thuMuc = thuMucUpload();
        try (Stream<Path> stream = Files.list(thuMuc)) {
            stream.map(path -> path.getFileName().toString())
                    .filter(ten -> !truocKhiUpload.contains(ten))
                    .forEach(ten -> {
                        try {
                            Files.deleteIfExists(thuMuc.resolve(ten));
                        } catch (IOException ignored) {
                            // dọn dẹp best-effort, không che lấp kết quả test
                        }
                    });
        }
    }

    private static class FakeTaiLieuRepository extends TaiLieuRepository {
        int luuCalls;
        int xoaCalls;
        int maYKien;
        String tenTep;
        String duongDan;
        String loaiTep;
        long kichThuoc;
        int lastMaTaiLieu;
        int lastMaYKien;
        TaiLieuResponse chiTiet;
        java.util.List<TaiLieuResponse> danhSach = java.util.List.of();
        RuntimeException luuError;

        FakeTaiLieuRepository() {
            super(null);
        }

        @Override
        public void luuThongTinTaiLieu(int maYKien, String tenTep, String duongDan,
                                       String loaiTep, long kichThuoc) {
            luuCalls++;
            this.maYKien = maYKien;
            this.tenTep = tenTep;
            this.duongDan = duongDan;
            this.loaiTep = loaiTep;
            this.kichThuoc = kichThuoc;
            if (luuError != null) {
                throw luuError;
            }
        }

        @Override
        public java.util.List<TaiLieuResponse> layTheoYKien(int maYKien) {
            lastMaYKien = maYKien;
            return danhSach;
        }

        @Override
        public TaiLieuResponse layChiTiet(int maTaiLieu) {
            return chiTiet;
        }

        @Override
        public void xoaTaiLieu(int maTaiLieu) {
            xoaCalls++;
            lastMaTaiLieu = maTaiLieu;
        }
    }
}
