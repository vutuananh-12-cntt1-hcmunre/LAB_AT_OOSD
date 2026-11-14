# Frontend — Hệ thống Tham vấn Chính sách

React + Vite. Giao diện trang trọng, font **Times New Roman**, tông màu navy / xám dịu.

## Chạy local

```bash
cd frontend
npm install
npm run dev
```

Mở http://localhost:5173

Backend Spring Boot mặc định proxy tới `http://localhost:8080` (cấu hình trong `vite.config.js`).

## Tài khoản mẫu (theo dữ liệu SQL)

- Cán bộ: `canbo@gov.vn` / `123456`
- Rà soát: `rasoat@gmail.com` / `123456`
- Người dân: đăng ký mới qua form

## Trang chính

| Đường dẫn | Mô tả |
|-----------|--------|
| `/` | Trang chủ |
| `/chu-de` | Danh sách chủ đề |
| `/chu-de/:id` | Chi tiết, nhóm YK, dự thảo |
| `/y-kien` | Danh sách ý kiến |
| `/y-kien/gui` | Gửi ý kiến + đính kèm |
| `/y-kien/:id` | Chi tiết, rà soát, phản hồi |
| `/thong-ke` | Thống kê (cán bộ) |
| `/cong-bo` | Công bố kết quả |
| `/quan-tri` | Cơ quan & người dùng (admin) |
