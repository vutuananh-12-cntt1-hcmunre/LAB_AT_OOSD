# HỆ THỐNG QUẢN LÝ NHÂN SỰ - CÔNG TY SƠN ĐÔNG Á
> **Môn học:** Phân tích & Thiết kế Hướng đối tượng (OOAD)
> **Bài thực hành:** Bài 7 - Quản lý Nhân sự  
> **Công nghệ sử dụng:** Java 17+, Spring Boot 3.x, Spring Data JPA, Thymeleaf, MS SQL Server, Bootstrap 5.

---

## 📋 GIỚI THIỆU HỆ THỐNG

Hệ thống hỗ trợ quản lý toàn bộ vòng đời nhân sự và các nghiệp vụ tài chính - tiền lương tại Công ty Sơn Đông Á:
* **Tuyển dụng & Thử việc:** Tiếp nhận hồ sơ, phân công thử việc theo phòng ban, lập Quyết định tuyển dụng thử việc.
* **Hợp đồng & Bảo hiểm:** Ký Hợp đồng lao động chính thức (Ngắn hạn/Dài hạn), quản lý hồ sơ Bảo hiểm Y tế (BHYT) & Bảo hiểm Xã hội (BHXH).
* **Quyết định Nhân sự:** Quản lý lịch sử diễn biến tăng lương, phụ cấp chức vụ, phụ cấp độc hại.
* **Phân công & Chấm công:** Lập kế hoạch phân công ca tuần, theo dõi làm phụ trội (tăng ca, ngày lễ, ngày nghỉ với hệ số 1.5) và chấm công hằng tháng.
* **Khen thưởng & Kỷ luật:** Ban hành các quyết định khen thưởng, phạt tiền đối với Cá nhân/Tập thể phòng ban và xuất Phiếu thu/chi tương ứng.
* **Bảng lương Hằng tháng:** Tự động tổng hợp ngày công, giờ phụ trội, phụ cấp và các khoản trích trừ bảo hiểm để tính thực lĩnh hằng tháng.

---

## 🛠️ CẤU TRÚC DỰ ÁN & CÔNG NGHỆ

```text
QuanLyNhanSu/
├── src/
│   ├── main/
│   │   ├── java/com/example/QuanLyNhanSu/
│   │   │   ├── controller/      # Controler xử lý các Request & View (TuyenDung, KhenThuong, DanhMuc, Home)
│   │   │   ├── entity/          # Các JPA Entity (NhanVien, PhongBan, QuyetDinhTuyenDung, QuyetDinhKTKL,...)
│   │   │   └── repository/      # Interface Spring Data JPA Repository
│   │   └── resources/
│   │       ├── templates/       # Giao diện Thymeleaf UI (style WinForms Win32)
│   │       │   ├── index.html       # Trang chủ FrmMain
│   │       │   ├── danh-muc.html    # FrmDanhMuc (Tab Phòng ban, Chức vụ,...)
│   │       │   ├── tuyen-dung.html  # FrmTuyenDung
│   │       │   └── khen-thuong.html # FrmKhenThuong
│   │       ├── application.properties # Cấu hình ứng dụng & SQL Server
│   │       └── data.sql         # Script nạp dữ liệu mẫu ban đầu
└── pom.xml                      # Quản lý Maven Dependencies