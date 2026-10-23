create database QuanLyNhanSu
go

use QuanLyNhanSu
go

CREATE TABLE PhongBan (
    MaPhongBan INT IDENTITY(1,1) PRIMARY KEY,
    TenPhongBan NVARCHAR(100) NOT NULL UNIQUE,
    MoTa NVARCHAR(255)
);
GO
CREATE TABLE NhanVien (
    MaNV INT IDENTITY(1,1) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    NgaySinh DATE NOT NULL,
    GioiTinh NVARCHAR(10),
    DiaChi NVARCHAR(255),
    SoDienThoai VARCHAR(15),
    Email VARCHAR(100),
    MaPhongBan INT NOT NULL,
    TrangThai NVARCHAR(30) DEFAULT N'Thử việc',
    FOREIGN KEY (MaPhongBan) REFERENCES PhongBan(MaPhongBan)
);
GO
CREATE TABLE QuyetDinhTuyenDung (
    SoQDTD INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT NOT NULL UNIQUE,
    NgayKy DATE NOT NULL DEFAULT GETDATE(),
    NgayBatDauThuViec DATE NOT NULL,
    NgayKetThucThuViec DATE NOT NULL,
    LuongThuViec DECIMAL(18,2) NOT NULL,
    NoiDungDieuKhoan NVARCHAR(MAX),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE HopDongLaoDong (
    SoHopDong VARCHAR(30) PRIMARY KEY,
    MaNV INT NOT NULL,
    LoaiHopDong NVARCHAR(30) NOT NULL CHECK (LoaiHopDong IN (N'Dài hạn', N'Ngắn hạn')),
    NgayKy DATE NOT NULL,
    NgayHieuLuc DATE NOT NULL,
    NgayHetHan DATE,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE HoSoBaoHiem (
    MaSoBH INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT NOT NULL UNIQUE,
    SoBHYT VARCHAR(20) NOT NULL UNIQUE,
    SoBHXH VARCHAR(20) NOT NULL UNIQUE,
    NgayCap DATE NOT NULL,
    MucDong DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE QuyetDinhChucVu (
    SoQDCV INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT NOT NULL,
    NgayHieuLuc DATE NOT NULL,
    ChucVuMoi NVARCHAR(100) NOT NULL,
    PhuCapChucVu DECIMAL(18,2) DEFAULT 0,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE QuyetDinhLuong (
    SoQDL INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT NOT NULL,
    NgayHieuLuc DATE NOT NULL,
    LuongCoBan DECIMAL(18,2) NOT NULL,
    PhuCapLuong DECIMAL(18,2) DEFAULT 0,
    PhuCapDocHai DECIMAL(18,2) DEFAULT 0,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE BangChamCong (
    MaBCC INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT NOT NULL,
    Thang INT NOT NULL,
    Nam INT NOT NULL,
    SoNgayCongChinh DECIMAL(4,1) NOT NULL DEFAULT 0,
    SoNgayNghi INT DEFAULT 0,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE PhieuGhiNhanPhuTroi (
    SoPhieuPT INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT NOT NULL,
    NgayPhuTroi DATE NOT NULL,
    SoGioPhuTroi DECIMAL(4,1) NOT NULL CHECK (SoGioPhuTroi > 0),
    HinhThucPT NVARCHAR(30) NOT NULL CHECK (HinhThucPT IN (N'Tăng ca', N'Ngày lễ', N'Ngày nghỉ')),
    HeSo DECIMAL(3,2) DEFAULT 1.5,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE QuyetDinhKhenThuongKyLuat (
    SoQDKTKL INT IDENTITY(1,1) PRIMARY KEY,
    LoaiQuyetDinh NVARCHAR(20) NOT NULL CHECK (LoaiQuyetDinh IN (N'Khen thưởng', N'Kỷ luật')),
    DoiTuong NVARCHAR(20) NOT NULL CHECK (DoiTuong IN (N'Cá nhân', N'Tập thể')),
    MaNV INT NULL,
    MaPhongBan INT NULL,
    NgayKy DATE NOT NULL DEFAULT GETDATE(),
    LyDo NVARCHAR(255) NOT NULL,
    SoTien DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaPhongBan) REFERENCES PhongBan(MaPhongBan)
);
GO
CREATE TABLE PhieuThuChi (
    SoPhieu INT IDENTITY(1,1) PRIMARY KEY,
    SoQDKTKL INT NOT NULL UNIQUE,
    LoaiPhieu NVARCHAR(10) NOT NULL CHECK (LoaiPhieu IN (N'Phiếu chi', N'Phiếu thu')),
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
    SoTien DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (SoQDKTKL) REFERENCES QuyetDinhKhenThuongKyLuat(SoQDKTKL)
);
GO

CREATE TABLE BangLuong (
    MaBangLuong INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT NOT NULL,
    Thang INT NOT NULL,
    Nam INT NOT NULL,
    LuongCoBan DECIMAL(18,2) NOT NULL,
    TienPhuTroi DECIMAL(18,2) DEFAULT 0,
    PhuCap DECIMAL(18,2) DEFAULT 0,
    KhenThuong DECIMAL(18,2) DEFAULT 0,
    KyLuat DECIMAL(18,2) DEFAULT 0,
    TrichBaoHiem DECIMAL(18,2) DEFAULT 0,
    ThucLinh DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    CONSTRAINT UC_BangLuong UNIQUE (MaNV, Thang, Nam)
);
GO
INSERT INTO PhongBan (TenPhongBan, MoTa) VALUES 
(N'Phòng Tài chính kế toán', N'Quản lý thu chi và tài chính'),
(N'Phòng Kỹ thuật', N'Nghiên cứu phát triển sơn'),
(N'Phòng Sản xuất', N'Sản xuất trực tiếp tại xưởng');

INSERT INTO NhanVien (HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, MaPhongBan, TrangThai) VALUES
(N'Nguyễn Văn A', '1995-03-10', N'Nam', N'TP. Hồ Chí Minh', '0901111222', 'nva@donga.com', 1, N'Chính thức'),
(N'Trần Thị B', '1998-07-20', N'Nữ', N'Bình Dương', '0903333444', 'ttb@donga.com', 2, N'Chính thức'),
(N'Lê Văn C', '2001-11-05', N'Nam', N'Đồng Nai', '0905555666', 'lvc@donga.com', 3, N'Thử việc');

INSERT INTO QuyetDinhTuyenDung (MaNV, NgayBatDauThuViec, NgayKetThucThuViec, LuongThuViec, NoiDungDieuKhoan) VALUES
(3, '2026-09-01', '2026-11-01', 7000000, N'Thử việc 2 tháng tại xưởng sản xuất sơn Đông Á');

INSERT INTO HopDongLaoDong (SoHopDong, MaNV, LoaiHopDong, NgayKy, NgayHieuLuc, NgayHetHan) VALUES
('HD2026/001', 1, N'Dài hạn', '2024-01-01', '2024-01-01', NULL),
('HD2026/002', 2, N'Ngắn hạn', '2026-01-01', '2026-01-01', '2026-12-31');

INSERT INTO HoSoBaoHiem (MaNV, SoBHYT, SoBHXH, NgayCap, MucDong) VALUES
(1, 'HC123456789', 'BH987654321', '2024-01-05', 1050000);

INSERT INTO QuyetDinhLuong (MaNV, NgayHieuLuc, LuongCoBan, PhuCapLuong, PhuCapDocHai) VALUES
(1, '2024-01-01', 12000000, 2000000, 0),
(2, '2026-01-01', 9000000, 1000000, 500000);

INSERT INTO QuyetDinhKhenThuongKyLuat (LoaiQuyetDinh, DoiTuong, MaNV, NgayKy, LyDo, SoTien) VALUES
(N'Khen thưởng', N'Cá nhân', 1, '2026-10-20', N'Hoàn thành xuất sắc nhiệm vụ được giao', 2000000);

INSERT INTO PhieuThuChi (SoQDKTKL, LoaiPhieu, SoTien) VALUES
(1, N'Phiếu chi', 2000000);
