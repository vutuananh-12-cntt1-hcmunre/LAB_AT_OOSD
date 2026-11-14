USE ThamVanChinhSach;
GO

CREATE OR ALTER PROCEDURE dbo.sp_DangKyNguoiDung
    @HoTen NVARCHAR(100),
    @Email VARCHAR(100),
    @MatKhau VARCHAR(255),
    @SoDienThoai VARCHAR(20) = NULL,
    @DiaChi NVARCHAR(255) = NULL,
    @MaVaiTro VARCHAR(20) = 'NGUOI_DAN',
    @MaCoQuan INT = NULL
AS
BEGIN

    IF EXISTS (SELECT 1 FROM NGUOI_DUNG WHERE Email = @Email)
    BEGIN
        RAISERROR(N'Email này đã được sử dụng trong hệ thống!');
        RETURN;
    END

    IF NOT EXISTS (SELECT 1 FROM dbo.VAI_TRO WHERE MaVaiTro = @MaVaiTro)
    BEGIN
        RAISERROR(N'Mã vai trò không tồn tại!', 16, 1);
        RETURN;
    END

    INSERT INTO dbo.NGUOI_DUNG (HoTen, Email, MatKhau, SoDienThoai, DiaChi, MaVaiTro, MaCoQuan)
    VALUES (@HoTen, @Email, @MatKhau, @SoDienThoai, @DiaChi, @MaVaiTro, @MaCoQuan);
END;
GO

USE ThamVanChinhSach;
GO

CREATE OR ALTER PROCEDURE dbo.sp_DangNhap
    @Email VARCHAR(100),
    @MatKhau VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Kiểm tra Email tồn tại
    IF NOT EXISTS (SELECT 1 FROM dbo.NGUOI_DUNG WHERE Email = @Email)
    BEGIN
        RAISERROR(N'Email không tồn tại trong hệ thống!', 16, 1);
        RETURN;
    END

    -- 2. Kiểm tra Trạng thái tài khoản (TrangThai = 1: Hoạt động, 0: Khóa)
    IF EXISTS (SELECT 1 FROM dbo.NGUOI_DUNG WHERE Email = @Email AND TrangThai = 0)
    BEGIN
        RAISERROR(N'Tài khoản của bạn đã bị khóa!', 16, 1);
        RETURN;
    END

    -- 3. Kiểm tra Mật khẩu
    IF NOT EXISTS (SELECT 1 FROM dbo.NGUOI_DUNG WHERE Email = @Email AND MatKhau = @MatKhau)
    BEGIN
        RAISERROR(N'Mật khẩu không chính xác!', 16, 1);
        RETURN;
    END

    -- 4. Trả về thông tin đăng nhập thành công
    SELECT 
        u.MaNguoiDung,
        u.HoTen,
        u.Email,
        u.SoDienThoai,
        u.DiaChi,
        u.MaVaiTro,
        v.TenVaiTro,
        u.MaCoQuan,
        c.TenCoQuan
    FROM dbo.NGUOI_DUNG u
    INNER JOIN dbo.VAI_TRO v ON u.MaVaiTro = v.MaVaiTro
    LEFT JOIN dbo.CO_QUAN c ON u.MaCoQuan = c.MaCoQuan
    WHERE u.Email = @Email AND u.MatKhau = @MatKhau;
END;
GO

USE ThamVanChinhSach;
GO

CREATE OR ALTER PROCEDURE dbo.sp_GuiYKienThamVan
    @MaChuDe INT,
    @NguoiGui INT,
    @NoiDung NVARCHAR(MAX)
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Kiểm tra chủ đề có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM dbo.CHU_DE_THAM_VAN WHERE MaChuDe = @MaChuDe)
    BEGIN
        RAISERROR(N'Chủ đề tham vấn không tồn tại!', 16, 1);
        RETURN;
    END

    -- 2. Kiểm tra thời gian tiếp nhận (BR03)
    IF NOT EXISTS (
        SELECT 1 FROM dbo.CHU_DE_THAM_VAN 
        WHERE MaChuDe = @MaChuDe 
          AND TrangThai = N'Đang mở'
          AND SYSDATETIME() BETWEEN ThoiGianBatDau AND ThoiGianKetThuc
    )
    BEGIN
        RAISERROR(N'Chủ đề tham vấn hiện không trong thời gian tiếp nhận ý kiến!', 16, 1);
        RETURN;
    END

    -- 3. Kiểm tra nội dung không rỗng (BR05)
    IF @NoiDung IS NULL OR LTRIM(RTRIM(@NoiDung)) = ''
    BEGIN
        RAISERROR(N'Nội dung ý kiến không được để trống!', 16, 1);
        RETURN;
    END

    -- 4. Thêm ý kiến mới (Trạng thái mặc định: Mới tiếp nhận)
    INSERT INTO dbo.Y_KIEN (MaChuDe, NguoiGui, NoiDung, TrangThaiXuLy)
    VALUES (@MaChuDe, @NguoiGui, @NoiDung, N'Mới tiếp nhận');

    -- 5. Trả về Mã ý kiến vừa tạo
    SELECT SCOPE_IDENTITY() AS MaYKienMoi;
END;
GO

USE ThamVanChinhSach;
GO

CREATE OR ALTER PROCEDURE dbo.sp_TaoChuDeThamVan
    @MaCoQuan INT,
    @TieuDe NVARCHAR(255),
    @NoiDungMoTa NVARCHAR(MAX),
    @ThoiGianBatDau DATETIME2(0),
    @ThoiGianKetThuc DATETIME2(0),
    @NguoiTao INT
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Kiểm tra quyền: Chỉ CAN_BO_CO_QUAN hoặc ADMIN mới được tạo chủ đề
    DECLARE @MaVaiTro VARCHAR(20);
    SELECT @MaVaiTro = MaVaiTro FROM dbo.NGUOI_DUNG WHERE MaNguoiDung = @NguoiTao;

    IF @MaVaiTro IS NULL OR @MaVaiTro NOT IN ('CAN_BO_CO_QUAN', 'ADMIN')
    BEGIN
        RAISERROR(N'Tài khoản của bạn không có quyền tạo chủ đề tham vấn!', 16, 1);
        RETURN;
    END

    -- 2. Thêm chủ đề mới nếu đúng quyền
    INSERT INTO dbo.CHU_DE_THAM_VAN (MaCoQuan, TieuDe, NoiDungMoTa, ThoiGianBatDau, ThoiGianKetThuc, TrangThai, NguoiTao)
    VALUES (@MaCoQuan, @TieuDe, @NoiDungMoTa, @ThoiGianBatDau, @ThoiGianKetThuc, N'Đang mở', @NguoiTao);

    SELECT SCOPE_IDENTITY() AS MaChuDeMoi;
END;
GO

USE ThamVanChinhSach;
GO

CREATE OR ALTER PROCEDURE dbo.sp_AIDeXuatNhomYKien
    @MaYKien INT,
    @MaNhom_AI_DeXuat INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra ý kiến có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM dbo.Y_KIEN WHERE MaYKien = @MaYKien)
    BEGIN
        RAISERROR(N'Ý kiến không tồn tại!', 16, 1);
        RETURN;
    END

    -- Cập nhật gợi ý của AI (KHÔNG chạm vào cột chính thức)
    UPDATE dbo.Y_KIEN
    SET MaNhom_AI_DeXuat = @MaNhom_AI_DeXuat,
        TrangThaiXuLy = N'Chờ rà soát'
    WHERE MaYKien = @MaYKien;
END;
GO

USE ThamVanChinhSach;
GO

CREATE OR ALTER PROCEDURE dbo.sp_RaSoatPhanNhom
    @MaYKien INT,
    @MaNhom_ChinhThuc INT,
    @NguoiRaSoat INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra Ý kiến tồn tại
    IF NOT EXISTS (SELECT 1 FROM dbo.Y_KIEN WHERE MaYKien = @MaYKien)
    BEGIN
        RAISERROR(N'Ý kiến không tồn tại!', 16, 1);
        RETURN;
    END

    -- Chốt nhóm chính thức và ghi nhận người rà soát
    UPDATE dbo.Y_KIEN
    SET MaNhom_ChinhThuc = @MaNhom_ChinhThuc,
        NguoiRaSoat = @NguoiRaSoat,
        ThoiDiemRaSoat = SYSDATETIME(),
        TrangThaiXuLy = N'Đã rà soát'
    WHERE MaYKien = @MaYKien;
END;
GO

USE ThamVanChinhSach;
GO

-- 1. Dọn dẹp Procedure cũ nếu đang bị lỗi kẹt lại
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'sp_PhanHoiYKien')
BEGIN
    DROP PROCEDURE dbo.sp_PhanHoiYKien;
END
GO

-- 2. Khởi tạo mới hoàn toàn Procedure chuẩn
CREATE PROCEDURE dbo.sp_PhanHoiYKien
    @MaYKien INT,
    @KetQuaXuLy NVARCHAR(50),
    @NoiDungGiaiThich NVARCHAR(MAX),
    @NguoiPhanHoi INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra quyền: Chỉ Cán bộ cơ quan hoặc Admin
    DECLARE @MaVaiTro VARCHAR(20);
    SELECT @MaVaiTro = MaVaiTro FROM dbo.NGUOI_DUNG WHERE MaNguoiDung = @NguoiPhanHoi;

    IF @MaVaiTro IS NULL OR @MaVaiTro NOT IN ('CAN_BO_CO_QUAN', 'ADMIN')
    BEGIN
        RAISERROR(N'Tài khoản không có quyền phản hồi ý kiến!', 16, 1);
        RETURN;
    END

    -- Thêm dữ liệu vào bảng PHAN_HOI
    INSERT INTO dbo.PHAN_HOI (MaYKien, KetQuaXuLy, NoiDungGiaiThich, NguoiPhanHoi, ThoiDiemPhanHoi)
    VALUES (@MaYKien, @KetQuaXuLy, @NoiDungGiaiThich, @NguoiPhanHoi, SYSDATETIME());

    -- Cập nhật trạng thái ý kiến
    UPDATE dbo.Y_KIEN
    SET TrangThaiXuLy = N'Đã phản hồi'
    WHERE MaYKien = @MaYKien;
END;
GO