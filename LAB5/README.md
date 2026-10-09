# Quản Lý Công Ty Du Lịch Văn Hóa Việt (Spring Boot)

Backend quản lý tour du lịch viết bằng **Spring Boot** kết nối **SQL Server**, hiện thực đầy đủ quy tắc nghiệp vụ theo mô hình OOAD[cite: 1, 2].

## 🛠 Công Nghệ
* **Java 17 / 21**, **Spring Boot 3.x** (Spring Data JPA, Hibernate)[cite: 2]
* **SQL Server** (LocalDB)[cite: 2, 25, 42]
* **Lombok**, **Maven**

## 📌 Quy Tắc Nghiệp Vụ Cốt Lõi
* **Khách lẻ:** Dưới 12 người (1 - 11), đăng ký theo chuyến mở bán, thanh toán vé ngay[cite: 2, 4].
* **Khách đoàn:** Trên 12 người, chọn ngày linh hoạt, bắt buộc đặt cọc; không đi mất cọc; mua bảo hiểm phải có danh sách người đi[cite: 3, 4].
* **Phân công HDV:** Chuyến lẻ tối đa 1 HDV; đoàn có thể nhiều HDV; cấm trùng lịch công tác[cite: 3, 4].
* **Thanh toán & Khảo sát:** Đoàn chỉ thanh toán tiền còn lại sau ngày kết thúc tour[cite: 3, 4]; phiếu khảo sát gửi sau tour (đánh giá 1 - 5 sao)[cite: 4].
* **Lương HDV:** Lương căn bản + thù lao các tour hoàn thành trong tháng[cite: 3, 4].

## 🚀 Cài Đặt & Chạy
1. Chạy file script SQL `QuanLyCongTyDuLich.sql` để tạo CSDL[cite: 25, 69].
2. Cấu hình chuỗi kết nối trong `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=QuanLyCongTyDuLich;encrypt=true;trustServerCertificate=true;
   spring.datasource.username=sa
   spring.datasource.password=YOUR_PASSWORD
   ```
3. Khởi chạy ứng dụng:
   ```bash
   mvn spring-boot:run
   ```

## 📡 REST APIs Chính
* `POST /api/booking/le`: Đăng ký & thanh toán vé khách lẻ[cite: 2, 3].
* `POST /api/booking/doan`: Lập phiếu đăng ký đoàn & danh sách bảo hiểm[cite: 3].
* `PUT /api/booking/doan/{id}/cancel`: Hủy tour đoàn (mất cọc, gỡ HDV)[cite: 3, 49].
* `POST /api/dispatch/assign`: Phân công HDV (kiểm tra trùng lịch)[cite: 3, 4].
* `POST /api/settlement/pay`: Thanh toán phần tiền còn lại sau tour[cite: 3, 4].
* `POST /api/settlement/survey/send`: Gửi phiếu khảo sát dịch vụ[cite: 3, 4].
* `GET /api/settlement/salary?thang=&nam=`: Tính bảng lương HDV theo tháng[cite: 3, 4].