create database QuanLyCaoOc
go

use QuanLyCaoOc
go


CREATE TABLE Tang (
    MaTang VARCHAR(10) PRIMARY KEY,
    TenTang NVARCHAR(50) NOT NULL,
    HeSoTang DECIMAL(3,2) DEFAULT 1.00
);
GO


CREATE TABLE Phong (
    MaPhong VARCHAR(10) PRIMARY KEY,
    MaTang VARCHAR(10) NOT NULL,
    HuongPhong NVARCHAR(50),
    DienTich DECIMAL(8,2) NOT NULL CHECK (DienTich > 0),
    DonGiaNiemYet DECIMAL(18,2) NOT NULL,
    TrangThai NVARCHAR(30) DEFAULT N'Trống' CHECK (TrangThai IN (N'Trống', N'Đã thuê', N'Bảo trì')),
    FOREIGN KEY (MaTang) REFERENCES Tang(MaTang)
);
GO


CREATE TABLE KhachHang (
    MaKH VARCHAR(10) PRIMARY KEY,
    TenCongTy NVARCHAR(150) NOT NULL,
    NguoiDaiDien NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(15) NOT NULL,
    Email VARCHAR(100),
    MaSoThue VARCHAR(20) UNIQUE
);
GO

CREATE TABLE HopDongThue (
    SoHopDong VARCHAR(20) PRIMARY KEY,
    MaKH VARCHAR(10) NOT NULL,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
    NgayBatDau DATE NOT NULL,
    ThoiHanThue INT NOT NULL CHECK (ThoiHanThue >= 6),
    TongTienThue6Thang DECIMAL(18,2) NOT NULL,
    TrangThai NVARCHAR(30) DEFAULT N'Hiệu lực',
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);
GO


CREATE TABLE ChiTietHopDong (
    SoHopDong VARCHAR(20) NOT NULL,
    MaPhong VARCHAR(10) NOT NULL,
    DonGiaThueThucTe DECIMAL(18,2) NOT NULL,
    PRIMARY KEY (SoHopDong, MaPhong),
    FOREIGN KEY (SoHopDong) REFERENCES HopDongThue(SoHopDong),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);
GO


CREATE TABLE PhieuGiaHan (
    SoPhieuGH VARCHAR(20) PRIMARY KEY,
    SoHopDong VARCHAR(20) NOT NULL,
    MaPhong VARCHAR(10) NOT NULL,
    NgayLapGH DATE NOT NULL DEFAULT GETDATE(),
    ThoiGianGiaHan INT NOT NULL CHECK (ThoiGianGiaHan > 0),
    TrangThai NVARCHAR(30) DEFAULT N'Đã duyệt',
    FOREIGN KEY (SoHopDong) REFERENCES HopDongThue(SoHopDong),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);
GO

CREATE TABLE HoaDonThuePhong (
    SoHDThue VARCHAR(20) PRIMARY KEY,
    SoHopDong VARCHAR(20) NOT NULL,
    Thang INT NOT NULL CHECK (Thang BETWEEN 1 AND 12),
    Nam INT NOT NULL,
    SoTienThue DECIMAL(18,2) NOT NULL,
    HanThanhToan DATE NOT NULL,
    TrangThaiThanhToan NVARCHAR(30) DEFAULT N'Chưa thanh toán' CHECK (TrangThaiThanhToan IN (N'Chưa thanh toán', N'Đã thanh toán')),
    FOREIGN KEY (SoHopDong) REFERENCES HopDongThue(SoHopDong)
);
GO


CREATE TABLE HoaDonDichVu (
    SoHDDV VARCHAR(20) PRIMARY KEY,
    SoHopDong VARCHAR(20) NOT NULL,
    Thang INT NOT NULL CHECK (Thang BETWEEN 1 AND 12),
    Nam INT NOT NULL,
    TienDien DECIMAL(18,2) DEFAULT 0,
    TienNuoc DECIMAL(18,2) DEFAULT 0,
    TienInternet DECIMAL(18,2) DEFAULT 0,
    TongTienDV DECIMAL(18,2) NOT NULL,
    TrangThaiThanhToan NVARCHAR(30) DEFAULT N'Chưa thanh toán' CHECK (TrangThaiThanhToan IN (N'Chưa thanh toán', N'Đã thanh toán')),
    FOREIGN KEY (SoHopDong) REFERENCES HopDongThue(SoHopDong)
);
GO


CREATE TABLE NhanVienCongTy (
    MaNV VARCHAR(10) PRIMARY KEY,
    MaKH VARCHAR(10) NOT NULL,
    MaPhong VARCHAR(10) NOT NULL,
    HoTen NVARCHAR(100) NOT NULL,
    HinhAnh NVARCHAR(255),
    ChucVu NVARCHAR(50),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);
GO


CREATE TABLE TheGuiXe (
    MaThe VARCHAR(20) PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL UNIQUE,
    BienSoXe VARCHAR(20) NOT NULL,
    LoaiXe NVARCHAR(30) DEFAULT N'Xe máy',
    NgayCap DATE NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (MaNV) REFERENCES NhanVienCongTy(MaNV)
);
GO

-- 11. Bảng Phiếu kiểm tra định kỳ (1 năm/lần)
CREATE TABLE PhieuKiemTra (
    SoPhieuKT VARCHAR(20) PRIMARY KEY,
    MaPhong VARCHAR(10) NOT NULL,
    NgayKiemTra DATE NOT NULL DEFAULT GETDATE(),
    TinhTrangPhong NVARCHAR(MAX) NOT NULL,
    NguoiKiemTra NVARCHAR(100),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);
GO


CREATE TABLE BienBanViPham (
    SoBienBan VARCHAR(20) PRIMARY KEY,
    MaPhong VARCHAR(10) NOT NULL,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
    LoiViPham NVARCHAR(MAX) NOT NULL,
    SoTienPhat DECIMAL(18,2) NOT NULL CHECK (SoTienPhat >= 0),
    TrangThaiPhat NVARCHAR(30) DEFAULT N'Chưa nộp' CHECK (TrangThaiPhat IN (N'Chưa nộp', N'Đã nộp')),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);
GO


INSERT INTO Tang (MaTang, TenTang, HeSoTang) VALUES 
('T01', N'Tầng 1', 1.20),
('T02', N'Tầng 2', 1.10),
('T05', N'Tầng 5', 1.00);

INSERT INTO Phong (MaPhong, MaTang, HuongPhong, DienTich, DonGiaNiemYet, TrangThai) VALUES
('P101', 'T01', N'Đông Nam', 120.0, 30000000, N'Đã thuê'),
('P201', 'T02', N'Tây Bắc', 85.0, 20000000, N'Đã thuê'),
('P501', 'T05', N'Nam', 150.0, 35000000, N'Trống');

INSERT INTO KhachHang (MaKH, TenCongTy, NguoiDaiDien, SoDienThoai, Email, MaSoThue) VALUES
('KH001', N'Công ty TNHH Giải Pháp Công Nghệ Việt', N'Nguyễn Văn An', '0908123456', 'an.nguyen@viettech.com', '0312345678'),
('KH002', N'Tập đoàn Tài chính Phú Mỹ', N'Trần Thị Mai', '0918987654', 'mai.tran@phumyfin.com', '0387654321');

INSERT INTO HopDongThue (SoHopDong, MaKH, NgayLap, NgayBatDau, ThoiHanThue, TongTienThue6Thang, TrangThai) VALUES
('HD2026/01', 'KH001', '2026-01-01', '2026-01-01', 12, 180000000, N'Hiệu lực'),
('HD2026/02', 'KH002', '2026-03-01', '2026-03-01', 6, 120000000, N'Hiệu lực');

INSERT INTO ChiTietHopDong (SoHopDong, MaPhong, DonGiaThueThucTe) VALUES
('HD2026/01', 'P101', 30000000),
('HD2026/02', 'P201', 20000000);

INSERT INTO NhanVienCongTy (MaNV, MaKH, MaPhong, HoTen, HinhAnh, ChucVu) VALUES
('NV001', 'KH001', 'P101', N'Lê Hoàng Nam', 'nv001.jpg', N'Trưởng phòng IT'),
('NV002', 'KH001', 'P101', N'Phạm Thu Hương', 'nv002.jpg', N'Lập trình viên');

INSERT INTO TheGuiXe (MaThe, MaNV, BienSoXe, LoaiXe, NgayCap) VALUES
('CARD-001', 'NV001', '59-S2 123.45', N'Xe máy Honda', '2026-01-02'),
('CARD-002', 'NV002', '59-K1 678.90', N'Xe máy Yamaha', '2026-01-05');