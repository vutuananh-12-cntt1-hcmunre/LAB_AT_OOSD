# 🏥 Hệ Thống Quản Lý Bệnh Viện (Hospital Management System)

Backend và hệ thống quản lý bệnh viện, hồ sơ bệnh án, khám chữa bệnh và kê toa thuốc. Dự án được phân tích, thiết kế theo phương pháp **Phân tích và Thiết kế Hướng đối tượng (OOAD)**, đồng thời hiện thực các quy tắc nghiệp vụ bằng cơ sở dữ liệu và giao diện chức năng.

## 👨‍🎓 Thông tin sinh viên

- **Sinh viên:** Vũ Tuấn Anh
- **Mã sinh viên:** `1250080012`

## 🎯 Mục tiêu dự án

- Quản lý thông tin bệnh nhân và hồ sơ bệnh án.
- Hỗ trợ đăng ký khám, khám bệnh và chỉ định xét nghiệm.
- Lưu trữ kết quả xét nghiệm, toa thuốc và chi tiết điều trị.
- Quản lý thanh toán phí quản lý hồ sơ hằng năm.
- Hỗ trợ quyền lợi khám sức khỏe định kỳ theo quý đối với bệnh nhân đủ điều kiện.
- Áp dụng các quy tắc nghiệp vụ nhất quán trong thiết kế hướng đối tượng và cơ sở dữ liệu.

## 🧰 Công nghệ sử dụng

- **Cơ sở dữ liệu:** Microsoft SQL Server, T-SQL, Constraints, Foreign Keys.
- **Giao diện Web / Local Application:** HTML5, CSS3, JavaScript, Local Server.
- **Backend Services:** Java hoặc C# (.NET).

> Công nghệ backend cụ thể và cách kết nối giao diện với backend phụ thuộc vào cấu hình triển khai thực tế của dự án.

## 📋 Quy tắc nghiệp vụ cốt lõi

### 1. Đăng ký bệnh nhân

- Bệnh nhân đến khám lần đầu phải cung cấp đầy đủ thông tin cá nhân và ảnh thẻ kích thước 3 × 4.
- Bệnh nhân thực hiện đóng phí quản lý hồ sơ hằng năm theo quy định của hệ thống.
- Sau khi đăng ký hợp lệ, hệ thống cấp một **mã hồ sơ bệnh án riêng** cho bệnh nhân.

### 2. Tái khám và tra cứu hồ sơ

- Khi tái khám, bệnh nhân cung cấp mã hồ sơ bệnh án để tra cứu thông tin.
- Hệ thống hỗ trợ xem lịch sử bệnh án, diễn biến bệnh và thông tin điều trị trước đó.
- Thông tin bệnh án cần được liên kết với đúng bệnh nhân để bảo đảm tính nhất quán.

### 3. Khám bệnh và chỉ định xét nghiệm

- Ghi nhận ngày giờ khám, bác sĩ phụ trách và khoa điều trị.
- Lưu thông tin sinh hiệu như nhiệt độ, huyết áp, cùng các triệu chứng của bệnh nhân.
- Bác sĩ có thể chỉ định xét nghiệm khi cần thiết.
- Yêu cầu xét nghiệm và kết quả xét nghiệm được quản lý, liên kết với phiếu khám tương ứng.

### 4. Toa thuốc và điều trị

- Bác sĩ lập toa thuốc dựa trên phiếu khám và kết quả xét nghiệm liên quan.
- Toa thuốc lưu danh sách thuốc cùng liều lượng, cách dùng, số lượng và số ngày sử dụng.
- Hệ thống lưu lời dặn của bác sĩ và liên kết toa thuốc với phiếu khám.

### 5. Khám sức khỏe định kỳ

- Bệnh nhân đã đóng phí quản lý hồ sơ năm được hưởng quyền lợi đặt lịch khám sức khỏe định kỳ theo quý.
- Các lượt khám định kỳ thuộc quyền lợi này không thu thêm phí khám.
- Điều kiện hưởng quyền lợi cần được kiểm tra theo trạng thái thanh toán và quy tắc nghiệp vụ của hệ thống.

## 🏗️ Phân tích và thiết kế hệ thống (OOAD)

### Quy tắc nghiệp vụ và quyết định triển khai

- Xác định **12 quy tắc nghiệp vụ**, ký hiệu `BR01` – `BR12`.
- Xác định **5 quyết định triển khai**, ký hiệu `QD01` – `QD05`.

### Mô hình lớp

Phân tích và thiết kế 16 lớp chính, bao gồm:

- `BenhNhan`
- `HoSoBenhAn`
- `PhieuKham`
- `ToaThuoc`
- `BacSi`
- `ThanhToanVienPhi`

Các lớp còn lại được tổ chức theo mô hình miền và trách nhiệm nghiệp vụ của hệ thống.

### Sơ đồ Use Case

Các sơ đồ đã xây dựng:

- Sơ đồ Use Case tổng quát toàn hệ thống.
- Sơ đồ phân rã Use Case **Đăng ký bệnh nhân**.
- Sơ đồ phân rã Use Case **Khám bệnh và chỉ định xét nghiệm**.
- Sơ đồ phân rã Use Case **Lập và nhận toa thuốc**.

### Sơ đồ động

- Sơ đồ tuần tự (Sequence Diagram) cho nghiệp vụ lập toa thuốc.
- Sơ đồ hoạt động (Activity Diagram) cho nghiệp vụ lập toa thuốc.

## 🗄️ Thiết kế cơ sở dữ liệu

Cơ sở dữ liệu được thiết kế với **12 bảng chính**, có quan hệ khóa ngoại để liên kết dữ liệu:

| STT | Bảng | Mục đích |
|---:|---|---|
| 1 | `BenhNhan` | Lưu thông tin bệnh nhân |
| 2 | `HoSoBenhAn` | Quản lý hồ sơ bệnh án |
| 3 | `DangKyKham` | Lưu thông tin đăng ký khám |
| 4 | `Khoa` | Quản lý khoa điều trị |
| 5 | `BacSi` | Lưu thông tin bác sĩ |
| 6 | `PhieuKham` | Ghi nhận thông tin khám bệnh |
| 7 | `YeuCauXetNghiem` | Lưu yêu cầu xét nghiệm |
| 8 | `KetQuaXetNghiem` | Lưu kết quả xét nghiệm |
| 9 | `Thuoc` | Quản lý danh mục thuốc |
| 10 | `ToaThuoc` | Lưu thông tin toa thuốc |
| 11 | `ChiTietToaThuoc` | Lưu thuốc và hướng dẫn sử dụng trong toa |
| 12 | `ThanhToan` | Quản lý thông tin thanh toán |

Cơ sở dữ liệu có script khởi tạo schema và dữ liệu mẫu (dummy data) cho các bảng.

## 🚀 Cài đặt và khởi tạo cơ sở dữ liệu

### Yêu cầu

- Microsoft SQL Server.
- SQL Server Management Studio (SSMS).
- File script SQL khởi tạo cơ sở dữ liệu, bảng và dữ liệu mẫu của dự án.

### Các bước thực hiện

1. Mở **SQL Server Management Studio (SSMS)** và kết nối tới SQL Server.
2. Tạo cơ sở dữ liệu mới bằng câu lệnh:

   ```sql
   CREATE DATABASE QuanLyBenhVienDB;
   GO
   ```

3. Chọn cơ sở dữ liệu vừa tạo:

   ```sql
   USE QuanLyBenhVienDB;
   GO
   ```

4. Mở file script SQL của dự án trong SSMS.
5. Kiểm tra thứ tự tạo bảng, khóa chính, khóa ngoại, constraints và dữ liệu mẫu.
6. Chạy script và kiểm tra kết quả trong Object Explorer hoặc bằng các câu lệnh truy vấn thử nghiệm.

> **Lưu ý:** Nếu file script đã có lệnh `CREATE DATABASE` và `USE`, hãy chạy theo đúng hướng dẫn trong file để tránh tạo cơ sở dữ liệu trùng lặp.

## 🖥️ Giao diện chức năng

### Đăng ký bệnh nhân

Giao diện hỗ trợ:

- Nhập thông tin cá nhân của bệnh nhân.
- Tải lên ảnh thẻ 3 × 4.
- Tiếp nhận thông tin đóng phí quản lý hồ sơ hằng năm.
- Phục vụ quy trình đăng ký và cấp mã hồ sơ bệnh án theo nghiệp vụ đã thiết kế.

### Lập toa thuốc

Giao diện hỗ trợ:

- Nhập mã phiếu khám để tra cứu thông tin khám.
- Thêm thuốc vào toa.
- Nhập liều lượng, cách dùng, số lượng và số ngày sử dụng.
- Ghi lời dặn của bác sĩ.

## ✅ Các hạng mục đã hoàn thành

- [x] Xác định 12 quy tắc nghiệp vụ (`BR01` – `BR12`).
- [x] Xác định 5 quyết định triển khai (`QD01` – `QD05`).
- [x] Phân tích 16 lớp chính và xây dựng Class Diagram.
- [x] Xây dựng Use Case tổng quát.
- [x] Phân rã Use Case đăng ký bệnh nhân.
- [x] Phân rã Use Case khám bệnh và chỉ định xét nghiệm.
- [x] Phân rã Use Case lập và nhận toa thuốc.
- [x] Xây dựng Sequence Diagram cho nghiệp vụ lập toa thuốc.
- [x] Xây dựng Activity Diagram cho nghiệp vụ lập toa thuốc.
- [x] Thiết kế schema gồm 12 bảng chính và các quan hệ khóa ngoại.
- [x] Viết script SQL tạo cấu trúc và dữ liệu mẫu.
- [x] Xây dựng giao diện đăng ký bệnh nhân.
- [x] Xây dựng giao diện lập toa thuốc.

## 📁 Cấu trúc dự án tham khảo

Cấu trúc thực tế có thể khác tùy theo cách tổ chức mã nguồn:

```text
Hospital-Management-System/
├── README.md
├── database/
│   └── QuanLyBenhVienDB.sql
├── docs/
│   ├── class-diagram
│   ├── use-case-diagrams
│   ├── sequence-diagram
│   └── activity-diagram
├── frontend/
│   ├── index.html
│   ├── css/
│   └── js/
└── backend/
    └── source-code/
```

## 🔒 Lưu ý về dữ liệu và triển khai

- Chỉ sử dụng dữ liệu giả lập khi thử nghiệm hoặc trình diễn.
- Hồ sơ bệnh án là dữ liệu nhạy cảm; môi trường thực tế cần có xác thực, phân quyền, nhật ký truy cập, sao lưu và biện pháp bảo vệ dữ liệu phù hợp.
- Cần kiểm tra tính hợp lệ của dữ liệu ở backend và cơ sở dữ liệu, không chỉ dựa vào kiểm tra trên giao diện.
- Những thông tin như thu phí, quyền lợi khám định kỳ và trạng thái điều trị cần tuân thủ các quy tắc nghiệp vụ đã thống nhất.

---

**Hospital Management System** — Dự án phân tích, thiết kế hướng đối tượng và quản lý dữ liệu phục vụ quy trình đăng ký, khám chữa bệnh và kê toa thuốc.
