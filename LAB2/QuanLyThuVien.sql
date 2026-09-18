CREATE DATABASE QuanLyThuVien;
GO
USE QuanLyThuVien;
GO
USE QuanLyThuVien;
GO

-- =============================================
-- 1. TẠO CÁC BẢNG DÀNH CHO DANH MỤC & THÔNG TIN CƠ BẢN
-- =============================================

-- Bảng Nhân Viên
CREATE TABLE nhan_vien (
    ma_nhan_vien VARCHAR(20) NOT NULL PRIMARY KEY,
    ho NVARCHAR(50) NOT NULL,
    ten NVARCHAR(50) NOT NULL,
    phai NVARCHAR(10) NOT NULL,
    ngay_sinh DATE NOT NULL,
    chuc_vu NVARCHAR(80) NOT NULL,
    so_dien_thoai VARCHAR(20) NULL
);

-- Bảng Thể Loại Sách
CREATE TABLE the_loai (
    ma_the_loai VARCHAR(20) NOT NULL PRIMARY KEY,
    ten_the_loai NVARCHAR(100) NOT NULL
);

-- Bảng Nhà Xuất Bản
CREATE TABLE nha_xuat_ban (
    ma_nha_xuat_ban VARCHAR(20) NOT NULL PRIMARY KEY,
    ten_nha_xuat_ban NVARCHAR(150) NOT NULL,
    dia_chi NVARCHAR(255) NULL,
    so_dien_thoai VARCHAR(20) NULL
);

-- Bảng Đầu Sách
CREATE TABLE dau_sach (
    ma_dau_sach VARCHAR(20) NOT NULL PRIMARY KEY,
    ten_sach NVARCHAR(200) NOT NULL,
    nam_xuat_ban INT NULL,
    so_luong_hien_co INT NOT NULL DEFAULT 0 CHECK (so_luong_hien_co >= 0),
    ma_the_loai VARCHAR(20) NOT NULL,
    ma_nha_xuat_ban VARCHAR(20) NOT NULL,
    CONSTRAINT FK_DauSach_TheLoai FOREIGN KEY (ma_the_loai) REFERENCES the_loai(ma_the_loai),
    CONSTRAINT FK_DauSach_NhaXuatBan FOREIGN KEY (ma_nha_xuat_ban) REFERENCES nha_xuat_ban(ma_nha_xuat_ban)
);

-- =============================================
-- 2. TẠO CÁC BẢNG ĐỘC GIẢ & THẺ THƯ VIỆN
-- =============================================

-- Bảng Độc Giả
CREATE TABLE doc_gia (
    ma_doc_gia VARCHAR(20) NOT NULL PRIMARY KEY,
    ho_ten NVARCHAR(100) NOT NULL,
    ngay_sinh DATE NULL,
    dia_chi NVARCHAR(255) NULL,
    so_dien_thoai VARCHAR(20) NULL,
    email VARCHAR(100) NULL
);

-- Bảng Thẻ Độc Giả
CREATE TABLE the_doc_gia (
    ma_the VARCHAR(20) NOT NULL PRIMARY KEY,
    ma_doc_gia VARCHAR(20) NOT NULL,
    ngay_cap DATE NOT NULL,
    han_su_dung DATE NOT NULL,
    da_dong_le_phi BIT NOT NULL DEFAULT 0,
    trang_thai BIT NOT NULL DEFAULT 1, -- 1: Đang hoạt động, 0: Khóa
    CONSTRAINT FK_TheDocGia_DocGia FOREIGN KEY (ma_doc_gia) REFERENCES doc_gia(ma_doc_gia)
);

-- =============================================
-- 3. TẠO CÁC BẢNG MƯỢN TRẢ SÁCH
-- =============================================

-- Bảng Phiếu Mượn
CREATE TABLE phieu_muon (
    ma_phieu_muon VARCHAR(50) NOT NULL PRIMARY KEY,
    ma_doc_gia VARCHAR(20) NOT NULL,
    ma_nhan_vien VARCHAR(20) NOT NULL,
    ngay_muon DATE NOT NULL DEFAULT GETDATE(),
    ngay_hen_tra DATE NOT NULL,
    CONSTRAINT FK_PhieuMuon_DocGia FOREIGN KEY (ma_doc_gia) REFERENCES doc_gia(ma_doc_gia),
    CONSTRAINT FK_PhieuMuon_NhanVien FOREIGN KEY (ma_nhan_vien) REFERENCES nhan_vien(ma_nhan_vien)
);

-- Bảng Chi Tiết Phiếu Mượn (Nhật ký mượn/trả từng cuốn)
CREATE TABLE chi_tiet_phieu_muon (
    ma_chi_tiet VARCHAR(50) NOT NULL PRIMARY KEY,
    ma_phieu_muon VARCHAR(50) NOT NULL,
    ma_dau_sach VARCHAR(20) NOT NULL,
    ngay_tra DATE NULL, -- NULL nghĩa là chưa trả sách
    tien_phat DECIMAL(18,2) DEFAULT 0.00,
    ghi_chu NVARCHAR(255) NULL,
    CONSTRAINT FK_CTPM_PhieuMuon FOREIGN KEY (ma_phieu_muon) REFERENCES phieu_muon(ma_phieu_muon),
    CONSTRAINT FK_CTPM_DauSach FOREIGN KEY (ma_dau_sach) REFERENCES dau_sach(ma_dau_sach)
);
GO

-- =============================================
-- 4. CHÈN DỮ LIỆU MẪU (DUMMY DATA)
-- =============================================

-- Thêm Nhân Viên
INSERT INTO nhan_vien (ma_nhan_vien, ho, ten, phai, ngay_sinh, chuc_vu, so_dien_thoai) VALUES
('NV01', N'Nguyễn', N'Văn A', N'Nam', '1990-05-15', N'Thủ thư', '0901234567'),
('NV02', N'Trần', N'Thị B', N'Nữ', '1995-10-20', N'Quản lý', '0912345678');

-- Thêm Thể Loại
INSERT INTO the_loai (ma_the_loai, ten_the_loai) VALUES
('TL01', N'Công nghệ thông tin'),
('TL02', N'Kinh tế - Quản lý'),
('TL03', N'Văn học dân gian');

-- Thêm Nhà Xuất Bản
INSERT INTO nha_xuat_ban (ma_nha_xuat_ban, ten_nha_xuat_ban, dia_chi, so_dien_thoai) VALUES
('NXB01', N'NXB Giáo Dục', N'123 Lý Thường Kiệt, TP.HCM', '02838111111'),
('NXB02', N'NXB Trẻ', N'161 Lý Chính Thắng, TP.HCM', '02838222222');

-- Thêm Đầu Sách
INSERT INTO dau_sach (ma_dau_sach, ten_sach, nam_xuat_ban, so_luong_hien_co, ma_the_loai, ma_nha_xuat_ban) VALUES
('S001', N'Lập trình Java căn bản', 2023, 10, 'TL01', 'NXB01'),
('S002', N'Thiết kế Spring Boot Web', 2024, 5, 'TL01', 'NXB02'),
('S003', N'Quản trị Kinh doanh 4.0', 2022, 3, 'TL02', 'NXB01');

-- Thêm Độc Giả & Thẻ
INSERT INTO doc_gia (ma_doc_gia, ho_ten, ngay_sinh, dia_chi, so_dien_thoai, email) VALUES
('DG01', N'Lê Văn C', '2001-01-01', N'Đồng Nai', '0988888888', 'vanc@gmail.com');

INSERT INTO the_doc_gia (ma_the, ma_doc_gia, ngay_cap, han_su_dung, da_dong_le_phi, trang_thai) VALUES
('THE01', 'DG01', '2026-01-01', '2027-01-01', 1, 1);
GO