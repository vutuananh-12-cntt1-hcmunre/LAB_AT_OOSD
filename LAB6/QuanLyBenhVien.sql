
-- 1. B?nh nhân
CREATE TABLE BenhNhan (
    MaBenhNhan INT IDENTITY(1,1) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    NgaySinh DATE NOT NULL,
    SoDienThoai VARCHAR(15) NOT NULL,
    Anh3x4 NVARCHAR(255)
);
GO
-- 2. H? s? b?nh án
CREATE TABLE HoSoBenhAn (
    MaHoSo INT IDENTITY(1,1) PRIMARY KEY,
    MaBenhNhan INT NOT NULL UNIQUE,
    MaHoSoCode VARCHAR(20) NOT NULL UNIQUE,
    NgayLap DATE NOT NULL DEFAULT GETDATE(),
    NgayHetHanPhi DATE,
    TrangThai NVARCHAR(30) NOT NULL DEFAULT N'?ang ho?t ??ng',
    FOREIGN KEY (MaBenhNhan)
        REFERENCES BenhNhan(MaBenhNhan)
);
GO

-- 3. ??ng ký khám
CREATE TABLE DangKyKham (
    MaDangKy INT IDENTITY(1,1) PRIMARY KEY,
    MaBenhNhan INT NOT NULL,
    NgayDangKy DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    LyDoKham NVARCHAR(255),
    TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Ch? khám',
    FOREIGN KEY (MaBenhNhan)
        REFERENCES BenhNhan(MaBenhNhan)
);
GO

-- 4. Khoa
CREATE TABLE Khoa (
    MaKhoa INT IDENTITY(1,1) PRIMARY KEY,
    TenKhoa NVARCHAR(100) NOT NULL UNIQUE
);
GO

-- 5. Bác s?
CREATE TABLE BacSi (
    MaBacSi INT IDENTITY(1,1) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(15),
    MaKhoa INT NOT NULL,
    FOREIGN KEY (MaKhoa)
        REFERENCES Khoa(MaKhoa)
);
GO

-- 6. Phi?u khám
CREATE TABLE PhieuKham (
    MaPhieuKham INT IDENTITY(1,1) PRIMARY KEY,
    MaHoSo INT NOT NULL,
    MaDangKy INT NOT NULL UNIQUE,
    MaBacSi INT NOT NULL,
    MaKhoa INT NOT NULL,
    NgayGioKham DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    TrieuChung NVARCHAR(500),
    NhietDo DECIMAL(4,1),
    HuyetAp VARCHAR(20),
    KetLuan NVARCHAR(1000),
    FOREIGN KEY (MaHoSo)
        REFERENCES HoSoBenhAn(MaHoSo),
    FOREIGN KEY (MaDangKy)
        REFERENCES DangKyKham(MaDangKy),
    FOREIGN KEY (MaBacSi)
        REFERENCES BacSi(MaBacSi),
    FOREIGN KEY (MaKhoa)
        REFERENCES Khoa(MaKhoa)
);
GO

-- 7. Yêu c?u xét nghi?m
CREATE TABLE YeuCauXetNghiem (
    MaYeuCau INT IDENTITY(1,1) PRIMARY KEY,
    MaPhieuKham INT NOT NULL,
    LoaiXetNghiem NVARCHAR(150) NOT NULL,
    NgayChiDinh DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    TrangThai NVARCHAR(30) NOT NULL DEFAULT N'Ch? th?c hi?n',
    FOREIGN KEY (MaPhieuKham)
        REFERENCES PhieuKham(MaPhieuKham)
);
GO

-- 8. K?t qu? xét nghi?m
CREATE TABLE KetQuaXetNghiem (
    MaKetQua INT IDENTITY(1,1) PRIMARY KEY,
    MaYeuCau INT NOT NULL UNIQUE,
    NgayThucHien DATETIME2,
    KetQua NVARCHAR(MAX),
    GhiChu NVARCHAR(500),
    FOREIGN KEY (MaYeuCau)
        REFERENCES YeuCauXetNghiem(MaYeuCau)
);
GO

-- 9. Thu?c
CREATE TABLE Thuoc (
    MaThuoc INT IDENTITY(1,1) PRIMARY KEY,
    TenThuoc NVARCHAR(150) NOT NULL,
    DonViTinh NVARCHAR(30) NOT NULL,
    MoTa NVARCHAR(500)
);
GO

-- 10. Toa thu?c
CREATE TABLE ToaThuoc (
    MaToaThuoc INT IDENTITY(1,1) PRIMARY KEY,
    MaPhieuKham INT NOT NULL UNIQUE,
    NgayKeToa DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    LoiDan NVARCHAR(1000),
    FOREIGN KEY (MaPhieuKham)
        REFERENCES PhieuKham(MaPhieuKham)
);
GO

-- 11. Chi ti?t toa thu?c
CREATE TABLE ChiTietToaThuoc (
    MaChiTiet INT IDENTITY(1,1) PRIMARY KEY,
    MaToaThuoc INT NOT NULL,
    MaThuoc INT NOT NULL,
    LieuLuong NVARCHAR(100) NOT NULL,
    CachDung NVARCHAR(255) NOT NULL,
    SoLuong INT NOT NULL CHECK (SoLuong > 0),
    SoNgayDung INT NOT NULL CHECK (SoNgayDung > 0),
    FOREIGN KEY (MaToaThuoc)
        REFERENCES ToaThuoc(MaToaThuoc),
    FOREIGN KEY (MaThuoc)
        REFERENCES Thuoc(MaThuoc),
    UNIQUE (MaToaThuoc, MaThuoc)
);
GO

-- 12. Thanh toán
CREATE TABLE ThanhToan (
    MaThanhToan INT IDENTITY(1,1) PRIMARY KEY,
    MaHoSo INT NULL,
    MaPhieuKham INT NULL,
    NgayThanhToan DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    SoTien DECIMAL(18,2) NOT NULL CHECK (SoTien >= 0),
    LoaiThanhToan NVARCHAR(30) NOT NULL,
    TrangThai NVARCHAR(30) NOT NULL DEFAULT N'?ã thanh toán',
    FOREIGN KEY (MaHoSo)
        REFERENCES HoSoBenhAn(MaHoSo),
    FOREIGN KEY (MaPhieuKham)
        REFERENCES PhieuKham(MaPhieuKham),
    CHECK (
        (MaHoSo IS NOT NULL AND MaPhieuKham IS NULL)
        OR
        (MaHoSo IS NULL AND MaPhieuKham IS NOT NULL)
    )
);
GO
