package com.example.ThamVanChinhSach.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.domain.BoGoiYPhanNhom;
import com.example.ThamVanChinhSach.domain.NhomYKien;
import com.example.ThamVanChinhSach.domain.PhanNhomGoiY;
import com.example.ThamVanChinhSach.domain.ViPhamNghiepVuException;
import com.example.ThamVanChinhSach.domain.YKien;
import com.example.ThamVanChinhSach.dto.ChiTietYKienResponse;
import com.example.ThamVanChinhSach.dto.DeXuatNhomResponse;
import com.example.ThamVanChinhSach.dto.RaSoatRequest;
import com.example.ThamVanChinhSach.dto.YKienCreateRequest;
import com.example.ThamVanChinhSach.dto.YKienResponse;
import com.example.ThamVanChinhSach.repository.TruyVetRepository;
import com.example.ThamVanChinhSach.repository.YKienRepository;

@Service
public class YKienService {
    private final YKienRepository yKienRepository;
    private final TruyVetRepository truyVetRepository;
    /** Bộ gợi ý là dịch vụ miền thuần Java, không phụ thuộc Spring/JDBC. */
    private final BoGoiYPhanNhom boGoiYPhanNhom = new BoGoiYPhanNhom();

    public YKienService(YKienRepository yKienRepository,
                        TruyVetRepository truyVetRepository) {
        this.yKienRepository = yKienRepository;
        this.truyVetRepository = truyVetRepository;
    }

    public String guiYKien(YKienCreateRequest request, int maNguoiDungFromToken) {
        try {
            Integer maYKienMoi = yKienRepository.guiYKien(
                    request.getMaChuDe(), maNguoiDungFromToken, request.getNoiDung());
            return "Gửi ý kiến thành công. Mã ý kiến mới: " + maYKienMoi;
        } catch (Exception e) {
            return "Gửi ý kiến thất bại: " + e.getMessage();
        }
    }

    /** Phân nhóm do cán bộ (người) rà soát xác nhận — không dùng AI để chốt nhóm. */
    public String raSoatYKien(int maYKien, RaSoatRequest request, int maNguoiRaSoat) {
        try {
            YKien yKien = yKienRepository.timTheoMa(maYKien);
            if (yKien == null) {
                return "Lỗi rà soát: Không tìm thấy ý kiến có ID: " + maYKien;
            }
            // Đối tượng miền tự kiểm tra: nhóm hợp lệ + bắt buộc có người rà soát.
            yKien.xacNhanRaSoat(request.getMaNhomChinhThuc(), maNguoiRaSoat, LocalDateTime.now());
            yKienRepository.raSoatPhanNhom(maYKien, request.getMaNhomChinhThuc(), maNguoiRaSoat);
            return "Cán bộ rà soát phân nhóm thành công cho ý kiến ID: " + maYKien;
        } catch (ViPhamNghiepVuException e) {
            return "Lỗi rà soát: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi rà soát: " + e.getMessage();
        }
    }

    public String phanHoiYKien(int maYKien, String ketQuaXuLy, String noiDungGiaiThich, int nguoiPhanHoi) {
        try {
            YKien yKien = yKienRepository.timTheoMa(maYKien);
            if (yKien == null) {
                return "Lỗi phản hồi ý kiến: Không tìm thấy ý kiến có ID: " + maYKien;
            }
            yKien.kiemTraPhanHoi(ketQuaXuLy, noiDungGiaiThich);
            yKienRepository.phanHoiYKien(maYKien, ketQuaXuLy, noiDungGiaiThich, nguoiPhanHoi);
            return "Phản hồi ý kiến thành công cho ý kiến ID: " + maYKien;
        } catch (ViPhamNghiepVuException e) {
            return "Lỗi phản hồi ý kiến: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi phản hồi ý kiến: " + e.getMessage();
        }
    }

    /**
     * Gợi ý phân nhóm: chỉ ghi vào nhóm đề xuất (MaNhom_AI_DeXuat).
     * Không chốt nhóm chính thức, không loại bỏ ý kiến. Nếu không đủ căn cứ thì
     * trả về kết quả rỗng để cán bộ rà soát chọn thủ công.
     */
    public DeXuatNhomResponse deXuatNhom(int maYKien) {
        YKien yKien = yKienRepository.timTheoMa(maYKien);
        if (yKien == null) {
            return null;
        }

        List<NhomYKien> danhSachNhom = yKienRepository.layNhomTheoChuDe(yKien.getMaChuDe());
        PhanNhomGoiY goiY = boGoiYPhanNhom.goiY(yKien.getNoiDung(), danhSachNhom);

        if (goiY == null) {
            return new DeXuatNhomResponse(maYKien, null, null, 0.0, List.of(),
                    "Chưa đủ căn cứ từ khoá để đề xuất nhóm. Cán bộ rà soát vui lòng chọn nhóm thủ công; "
                            + "hệ thống không tự loại ý kiến.");
        }

        yKien.ghiNhanDeXuatNhom(goiY.maNhom());
        yKienRepository.aiDeXuatNhom(maYKien, goiY.maNhom());

        return new DeXuatNhomResponse(maYKien,
                goiY.maNhom(),
                goiY.tenNhom(),
                goiY.doTinCay(),
                goiY.tuKhoaKhop(),
                "Đây chỉ là đề xuất tự động (trùng khớp từ khoá), KHÔNG phải phân nhóm chính thức. "
                        + "Cán bộ rà soát phải xác nhận qua API /api/ykien/{maYKien}/ra-soat.");
    }

    public List<YKienResponse> layDanhSachYKien() {
        return yKienRepository.layDanhSachYKien();
    }

    public ChiTietYKienResponse layChiTietYKien(int maYKien) {
        ChiTietYKienResponse chiTiet = yKienRepository.layChiTietYKien(maYKien);
        if (chiTiet != null) {
            chiTiet.setDuThaoLienKet(truyVetRepository.layDuThaoTiepThuYKien(maYKien));
        }
        return chiTiet;
    }

    public List<YKienResponse> layDanhSachYKienCaNhan(int nguoiGui) {
        return yKienRepository.layDanhSachYKienCaNhan(nguoiGui);
    }
}
