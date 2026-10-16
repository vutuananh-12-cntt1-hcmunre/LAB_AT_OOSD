
package lab6.controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BenhVienController {

    private final JdbcTemplate jdbc;

    public BenhVienController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/")
    public String trangChu() {
        return "redirect:/dang-ky-benh-nhan";
    }

    @GetMapping("/dang-ky-benh-nhan")
    public String dangKyBenhNhan() {
        return "dang-ky-benh-nhan";
    }

    @PostMapping("/dang-ky-benh-nhan")
    @Transactional
    public String luuBenhNhan(
            @RequestParam String hoTen,
            @RequestParam String ngaySinh,
            @RequestParam String diaChi,
            @RequestParam String soDienThoai,
            @RequestParam(defaultValue = "") String anh3x4,
            @RequestParam(defaultValue = "200000") int phiHoSo) {

        // 1. Lưu thông tin bệnh nhân
        KeyHolder benhNhanKey = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO BenhNhan " +
                "(HoTen, DiaChi, NgaySinh, SoDienThoai, Anh3x4) " +
                "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, hoTen);
            ps.setString(2, diaChi);
            ps.setDate(3, Date.valueOf(LocalDate.parse(ngaySinh)));
            ps.setString(4, soDienThoai);
            ps.setString(5, anh3x4);

            return ps;
        }, benhNhanKey);

        Number maBenhNhan = benhNhanKey.getKey();

        if (maBenhNhan == null) {
            throw new IllegalStateException("Không tạo được mã bệnh nhân");
        }

        // 2. Tạo mã hồ sơ bệnh án
        String maHoSoCode = String.format(
            "HS%04d", maBenhNhan.intValue()
        );

        LocalDate ngayLap = LocalDate.now();
        LocalDate ngayHetHanPhi = ngayLap.plusYears(1);

        KeyHolder hoSoKey = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO HoSoBenhAn " +
                "(MaBenhNhan, MaHoSoCode, NgayLap, " +
                "NgayHetHanPhi, TrangThai) " +
                "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, maBenhNhan.intValue());
            ps.setString(2, maHoSoCode);
            ps.setDate(3, Date.valueOf(ngayLap));
            ps.setDate(4, Date.valueOf(ngayHetHanPhi));
            ps.setString(5, "Đang hoạt động");

            return ps;
        }, hoSoKey);

        Number maHoSo = hoSoKey.getKey();

        if (maHoSo == null) {
            throw new IllegalStateException("Không tạo được hồ sơ");
        }

        // 3. Ghi nhận phí quản lý hồ sơ hằng năm
        jdbc.update(
            "INSERT INTO ThanhToan " +
            "(MaHoSo, MaPhieuKham, NgayThanhToan, " +
            "SoTien, LoaiThanhToan, TrangThai) " +
            "VALUES (?, NULL, ?, ?, ?, ?)",
            maHoSo.intValue(),
            Date.valueOf(ngayLap),
            phiHoSo,
            "Phí quản lý hồ sơ năm",
            "Đã thanh toán"
        );

        return "redirect:/dang-ky-benh-nhan?success";
    }

    @GetMapping("/kham-benh")
    public String khamBenh(Model model) {
        List<Map<String, Object>> danhSachThuoc = jdbc.queryForList(
            "SELECT MaThuoc, TenThuoc FROM Thuoc"
        );

        model.addAttribute("danhSachThuoc", danhSachThuoc);

        return "kham-benh";
    }

    @PostMapping("/kham-benh/luu-toa")
    @Transactional
    public String luuToaThuoc(
            @RequestParam int maPhieuKham,
            @RequestParam(defaultValue = "") String loiDan,

            @RequestParam(defaultValue = "") String maThuoc1,
            @RequestParam(defaultValue = "") String lieuLuong1,
            @RequestParam(defaultValue = "") String cachDung1,
            @RequestParam(defaultValue = "0") int soLuong1,
            @RequestParam(defaultValue = "0") int soNgayDung1,

            @RequestParam(defaultValue = "") String maThuoc2,
            @RequestParam(defaultValue = "") String lieuLuong2,
            @RequestParam(defaultValue = "") String cachDung2,
            @RequestParam(defaultValue = "0") int soLuong2,
            @RequestParam(defaultValue = "0") int soNgayDung2,

            @RequestParam(defaultValue = "") String maThuoc3,
            @RequestParam(defaultValue = "") String lieuLuong3,
            @RequestParam(defaultValue = "") String cachDung3,
            @RequestParam(defaultValue = "0") int soLuong3,
            @RequestParam(defaultValue = "0") int soNgayDung3) {

        String[] maThuoc = {maThuoc1, maThuoc2, maThuoc3};
        String[] lieuLuong = {lieuLuong1, lieuLuong2, lieuLuong3};
        String[] cachDung = {cachDung1, cachDung2, cachDung3};
        int[] soLuong = {soLuong1, soLuong2, soLuong3};
        int[] soNgayDung = {soNgayDung1, soNgayDung2, soNgayDung3};

        // Phải chọn ít nhất một loại thuốc
        boolean coThuoc = false;

        for (String ma : maThuoc) {
            if (!ma.isBlank()) {
                coThuoc = true;
                break;
            }
        }

        if (!coThuoc) {
            throw new IllegalArgumentException(
                "Phải chọn ít nhất một loại thuốc"
            );
        }

        // 1. Lưu toa thuốc
        KeyHolder toaKey = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO ToaThuoc " +
                "(MaPhieuKham, NgayKeToa, LoiDan) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, maPhieuKham);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setString(3, loiDan);

            return ps;
        }, toaKey);

        Number maToa = toaKey.getKey();

        if (maToa == null) {
            throw new IllegalStateException("Không tạo được mã toa thuốc");
        }

        // 2. Lưu các dòng chi tiết thuốc
        for (int i = 0; i < maThuoc.length; i++) {
            if (maThuoc[i].isBlank()) {
                continue;
            }

            if (soLuong[i] <= 0 || soNgayDung[i] <= 0
                    || lieuLuong[i].isBlank()
                    || cachDung[i].isBlank()) {
                throw new IllegalArgumentException(
                    "Vui lòng nhập đầy đủ thông tin từng loại thuốc"
                );
            }

            jdbc.update(
                "INSERT INTO ChiTietToaThuoc " +
                "(MaToaThuoc, MaThuoc, LieuLuong, CachDung, " +
                "SoLuong, SoNgayDung) VALUES (?, ?, ?, ?, ?, ?)",
                maToa.intValue(),
                Integer.parseInt(maThuoc[i]),
                lieuLuong[i],
                cachDung[i],
                soLuong[i],
                soNgayDung[i]
            );
        }

        return "redirect:/kham-benh?success";
    }
}
