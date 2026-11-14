package com.example.ThamVanChinhSach.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ThamVanChinhSach.dto.TaiLieuResponse;
import com.example.ThamVanChinhSach.repository.TaiLieuRepository;

@Service
public class TaiLieuService {
    private final TaiLieuRepository taiLieuRepository;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public TaiLieuService(TaiLieuRepository taiLieuRepository) {
        this.taiLieuRepository = taiLieuRepository;
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String luuFileTaiLieu(int maYKien, MultipartFile file) {
        try {
            if (file.isEmpty()) return "Lỗi: File rỗng!";

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                return "Lỗi: Tên file không hợp lệ!";
            }
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;

            Path filePath = Paths.get(UPLOAD_DIR + newFilename);
            Files.write(filePath, file.getBytes());

            try {
                taiLieuRepository.luuThongTinTaiLieu(maYKien, originalFilename,
                        "/uploads/" + newFilename, file.getContentType(), file.getSize());
            } catch (RuntimeException e) {
                // CSDL từ chối (FK/trigger): xóa file vừa ghi để không để lại file mồ côi.
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException ignored) {
                    // giữ nguyên lỗi gốc của CSDL
                }
                throw e;
            }

            return "Upload thành công file: " + originalFilename;
        } catch (IOException e) {
            return "Lỗi lưu file: " + e.getMessage();
        } catch (RuntimeException e) {
            return "Lỗi lưu tài liệu: " + e.getMessage();
        }
    }

    public List<TaiLieuResponse> layTheoYKien(int maYKien) {
        return taiLieuRepository.layTheoYKien(maYKien);
    }

    public String xoaTaiLieu(int maTaiLieu) {
        try {
            TaiLieuResponse tl = taiLieuRepository.layChiTiet(maTaiLieu);
            if (tl == null) {
                return "Lỗi: Không tìm thấy tài liệu ID: " + maTaiLieu;
            }
            // Xóa file vật lý nếu tồn tại
            if (tl.getDuongDanTep() != null) {
                Path path = Paths.get(System.getProperty("user.dir") + tl.getDuongDanTep());
                Files.deleteIfExists(path);
            }
            taiLieuRepository.xoaTaiLieu(maTaiLieu);
            return "Xóa tài liệu thành công!";
        } catch (Exception e) {
            return "Lỗi xóa tài liệu: " + e.getMessage();
        }
    }
}
