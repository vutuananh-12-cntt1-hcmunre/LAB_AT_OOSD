package com.example.quanlythuvien.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quanlythuvien.entity.ChiTietPhieuMuon;
import com.example.quanlythuvien.entity.DauSach;
import com.example.quanlythuvien.entity.DocGia;
import com.example.quanlythuvien.entity.NhanVien;
import com.example.quanlythuvien.entity.PhieuMuon;
import com.example.quanlythuvien.entity.TheDocGia;
import com.example.quanlythuvien.repository.ChiTietPhieuMuonRepository;
import com.example.quanlythuvien.repository.DauSachRepository;
import com.example.quanlythuvien.repository.DocGiaRepository;
import com.example.quanlythuvien.repository.NhanVienRepository;
import com.example.quanlythuvien.repository.PhieuMuonRepository;
import com.example.quanlythuvien.repository.TheDocGiaRepository;

@Service
public class MuonTraService {

    private final PhieuMuonRepository phieuMuonRepository;
    private final ChiTietPhieuMuonRepository chiTietRepository;
    private final DauSachRepository dauSachRepository;
    private final DocGiaRepository docGiaRepository;
    private final NhanVienRepository nhanVienRepository;
    private final TheDocGiaRepository theDocGiaRepository;

    public MuonTraService(
            PhieuMuonRepository phieuMuonRepository,
            ChiTietPhieuMuonRepository chiTietRepository,
            DauSachRepository dauSachRepository,
            DocGiaRepository docGiaRepository,
            NhanVienRepository nhanVienRepository,
            TheDocGiaRepository theDocGiaRepository) {

        this.phieuMuonRepository = phieuMuonRepository;
        this.chiTietRepository = chiTietRepository;
        this.dauSachRepository = dauSachRepository;
        this.docGiaRepository = docGiaRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.theDocGiaRepository = theDocGiaRepository;
    }

    // ==========================================
    // LẤY DANH SÁCH PHIẾU MƯỢN
    // ==========================================

    public List<PhieuMuon> findAll() {

        return phieuMuonRepository.findAll();
    }

    // ==========================================
    // LẤY CHI TIẾT THEO MÃ PHIẾU MƯỢN
    // ==========================================

    public List<ChiTietPhieuMuon> findChiTiet(
            String maPhieuMuon) {

        return chiTietRepository
                .findByPhieuMuon_MaPhieuMuon(maPhieuMuon);
    }

    // ==========================================
    // LẤY CHI TIẾT THEO MÃ CHI TIẾT
    // ==========================================

    public ChiTietPhieuMuon findChiTietById(
            String id) {

        return chiTietRepository
                .findById(id)
                .orElse(null);
    }

    // ==========================================
    // LẤY SÁCH ĐANG MƯỢN
    // ==========================================

    public List<ChiTietPhieuMuon> findSachDangMuon(
            String maDocGia) {

        return chiTietRepository
                .findByPhieuMuon_DocGia_MaDocGiaAndNgayTraIsNull(
                        maDocGia
                );
    }

    // ==========================================
    // TẠO PHIẾU MƯỢN
    // ==========================================

    @Transactional
    public void taoPhieuMuon(
            String maPhieuMuon,
            String maDocGia,
            String maNhanVien,
            LocalDate ngayMuon,
            LocalDate ngayHenTra,
            List<String> danhSachMaDauSach) {

        // --------------------------------------
        // 1. Kiểm tra dữ liệu cơ bản
        // --------------------------------------

        if (maPhieuMuon == null ||
                maPhieuMuon.trim().isEmpty()) {

            throw new RuntimeException(
                    "Mã phiếu mượn không được để trống."
            );
        }

        if (ngayMuon == null ||
                ngayHenTra == null) {

            throw new RuntimeException(
                    "Ngày mượn và ngày hẹn trả không được để trống."
            );
        }

        // BR08
        if (ngayHenTra.isBefore(ngayMuon)) {

            throw new RuntimeException(
                    "Ngày hẹn trả không được trước ngày mượn."
            );
        }

        // --------------------------------------
        // 2. Tìm độc giả
        // --------------------------------------

        DocGia docGia =
                docGiaRepository.findById(maDocGia)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy độc giả."
                                )
                        );

        // --------------------------------------
        // 3. Tìm nhân viên
        // --------------------------------------

        NhanVien nhanVien =
                nhanVienRepository.findById(maNhanVien)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên."
                                )
                        );

        // --------------------------------------
        // 4. Kiểm tra thẻ độc giả
        // --------------------------------------

        TheDocGia theDocGia =
                theDocGiaRepository
                        .findByDocGia_MaDocGiaAndTrangThaiTrue(
                                maDocGia
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Độc giả chưa có thẻ đang hoạt động."
                                )
                        );

        // BR03 - kiểm tra hạn sử dụng
        if (theDocGia.getHanSuDung()
                .isBefore(ngayMuon)) {

            throw new RuntimeException(
                    "Thẻ độc giả đã hết hạn."
            );
        }

        // BR03 - kiểm tra lệ phí
        if (!Boolean.TRUE.equals(
                theDocGia.getDaDongLePhi())) {

            throw new RuntimeException(
                    "Thẻ độc giả chưa đóng lệ phí."
            );
        }

        // --------------------------------------
        // 5. Kiểm tra sách đang quá hạn
        // --------------------------------------

        List<ChiTietPhieuMuon> dangMuon =
                findSachDangMuon(maDocGia);

        for (ChiTietPhieuMuon chiTiet : dangMuon) {

            PhieuMuon phieuMuon =
                    chiTiet.getPhieuMuon();

            if (phieuMuon.getNgayHenTra()
                    .isBefore(ngayMuon)) {

                throw new RuntimeException(
                        "Độc giả đang có sách quá hạn chưa trả."
                );
            }
        }

        // --------------------------------------
        // 6. Kiểm tra danh sách sách
        // --------------------------------------

        if (danhSachMaDauSach == null ||
                danhSachMaDauSach.isEmpty()) {

            throw new RuntimeException(
                    "Phải chọn ít nhất một đầu sách."
            );
        }

        // BR04
        if (danhSachMaDauSach.size() > 3) {

            throw new RuntimeException(
                    "Mỗi lần chỉ được mượn tối đa 3 đầu sách."
            );
        }

        // --------------------------------------
        // 7. Không cho trùng sách
        // --------------------------------------

        Set<String> tapMaSach =
                new HashSet<>(danhSachMaDauSach);

        // BR06
        if (tapMaSach.size()
                != danhSachMaDauSach.size()) {

            throw new RuntimeException(
                    "Không được chọn trùng đầu sách."
            );
        }

        // --------------------------------------
        // 8. Kiểm tra mã phiếu đã tồn tại
        // --------------------------------------

        if (phieuMuonRepository
                .existsById(maPhieuMuon.trim())) {

            throw new RuntimeException(
                    "Mã phiếu mượn đã tồn tại."
            );
        }

        // --------------------------------------
        // 9. Tạo phiếu mượn
        // --------------------------------------

        PhieuMuon phieuMuon =
                new PhieuMuon();

        phieuMuon.setMaPhieuMuon(
                maPhieuMuon.trim()
        );

        phieuMuon.setDocGia(docGia);

        phieuMuon.setNhanVien(nhanVien);

        phieuMuon.setNgayMuon(ngayMuon);

        phieuMuon.setNgayHenTra(ngayHenTra);

        phieuMuonRepository.save(phieuMuon);

        // --------------------------------------
        // 10. Tạo chi tiết phiếu
        // --------------------------------------

        int stt = 1;

        for (String maDauSach :
                danhSachMaDauSach) {

            DauSach dauSach =
                    dauSachRepository
                            .findById(maDauSach)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Không tìm thấy đầu sách: "
                                                    + maDauSach
                                    )
                            );

            // BR07
            if (dauSach.getSoLuongHienCo() <= 0) {

                throw new RuntimeException(
                        "Đầu sách \""
                                + dauSach.getTenSach()
                                + "\" đã hết trong kho."
                );
            }

            // ----------------------------------
            // Giảm số lượng sách trong kho
            // ----------------------------------

            dauSach.setSoLuongHienCo(
                    dauSach.getSoLuongHienCo() - 1
            );

            dauSachRepository.save(dauSach);

            // ----------------------------------
            // Tạo mã chi tiết
            // ----------------------------------

            String maChiTiet =
                    maPhieuMuon.trim()
                            + "-CT"
                            + stt;

            ChiTietPhieuMuon chiTiet =
                    new ChiTietPhieuMuon();

            chiTiet.setMaChiTiet(maChiTiet);

            chiTiet.setPhieuMuon(phieuMuon);

            chiTiet.setDauSach(dauSach);

            chiTiet.setNgayTra(null);

            chiTiet.setTienPhat(
                    BigDecimal.ZERO
            );

            chiTiet.setGhiChu(null);

            chiTietRepository.save(chiTiet);

            stt++;
        }
    }

    // ==========================================
    // TRẢ SÁCH
    // ==========================================

    @Transactional
    public void traSach(
            String maChiTiet,
            LocalDate ngayTra,
            BigDecimal tienPhat,
            String ghiChu) {

        // --------------------------------------
        // 1. Tìm chi tiết phiếu
        // --------------------------------------

        ChiTietPhieuMuon chiTiet =
                chiTietRepository
                        .findById(maChiTiet)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy chi tiết phiếu mượn."
                                )
                        );

        // --------------------------------------
        // 2. Kiểm tra đã trả chưa
        // --------------------------------------

        if (chiTiet.getNgayTra() != null) {

            throw new RuntimeException(
                    "Sách này đã được trả."
            );
        }

        // --------------------------------------
        // 3. Nếu không nhập ngày trả
        // --------------------------------------

        if (ngayTra == null) {

            ngayTra = LocalDate.now();
        }

        // --------------------------------------
        // 4. Kiểm tra ngày trả
        // --------------------------------------

        PhieuMuon phieuMuon =
                chiTiet.getPhieuMuon();

        if (ngayTra.isBefore(
                phieuMuon.getNgayMuon())) {

            throw new RuntimeException(
                    "Ngày trả không được trước ngày mượn."
            );
        }

        // --------------------------------------
        // 5. Tiền phạt
        // --------------------------------------

        if (tienPhat == null) {

            tienPhat = BigDecimal.ZERO;
        }

        // BR09
        if (tienPhat.compareTo(
                BigDecimal.ZERO) > 0
                &&
                (ghiChu == null ||
                        ghiChu.trim().isEmpty())) {

            throw new RuntimeException(
                    "Có tiền phạt thì phải ghi rõ lý do."
            );
        }

        // --------------------------------------
        // 6. Cập nhật thông tin trả sách
        // --------------------------------------

        chiTiet.setNgayTra(ngayTra);

        chiTiet.setTienPhat(tienPhat);

        chiTiet.setGhiChu(
                ghiChu == null
                        ? null
                        : ghiChu.trim()
        );

        chiTietRepository.save(chiTiet);

        // --------------------------------------
        // 7. Trả sách về kho
        // --------------------------------------

        DauSach dauSach =
                chiTiet.getDauSach();

        dauSach.setSoLuongHienCo(
                dauSach.getSoLuongHienCo() + 1
        );

        dauSachRepository.save(dauSach);
    }
}