USE ThamVanChinhSach;
GO

CREATE OR ALTER TRIGGER dbo.TR_Y_KIEN_KIEM_TRA_THOI_GIAN
ON dbo.Y_KIEN
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    IF EXISTS (
        SELECT 1
        FROM inserted i
        INNER JOIN dbo.CHU_DE_THAM_VAN c ON c.MaChuDe = i.MaChuDe
        WHERE c.TrangThai <> N'Đang mở'
           OR i.ThoiDiemGui < c.ThoiGianBatDau
           OR i.ThoiDiemGui > c.ThoiGianKetThuc
    )
    BEGIN
        THROW 51001, N'Không thể gửi ý kiến: chủ đề chưa mở, đã đóng hoặc ngoài thời gian tham vấn.', 1;
    END
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_Y_KIEN_BAO_VE_NHOM_CHINH_THUC
ON dbo.Y_KIEN
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF UPDATE(MaNhom_ChinhThuc)
       OR UPDATE(NguoiRaSoat)
       OR UPDATE(ThoiDiemRaSoat)
       OR UPDATE(TrangThaiXuLy)
    BEGIN
        IF EXISTS (
            SELECT 1
            FROM inserted i
            INNER JOIN deleted d ON d.MaYKien = i.MaYKien
            WHERE i.MaNhom_ChinhThuc IS NOT NULL
              AND (
                    i.NguoiRaSoat IS NULL
                    OR i.ThoiDiemRaSoat IS NULL
                    OR i.TrangThaiXuLy NOT IN (N'Đã rà soát', N'Đã phản hồi')
                  )
        )
        BEGIN
            THROW 51002, N'Nhóm chính thức cần có người rà soát, thời điểm rà soát và trạng thái phù hợp.', 1;
        END
    END
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_NHOM_Y_KIEN_BAO_VE_CHU_DE
ON dbo.NHOM_Y_KIEN
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF UPDATE(MaChuDe)
       AND EXISTS (
            SELECT 1
            FROM inserted i
            INNER JOIN dbo.Y_KIEN y
                ON y.MaNhom_AI_DeXuat = i.MaNhom
                OR y.MaNhom_ChinhThuc = i.MaNhom
            WHERE i.MaChuDe <> (
                SELECT d.MaChuDe
                FROM deleted d
                WHERE d.MaNhom = i.MaNhom
            )
       )
    BEGIN
        THROW 51003, N'Không thể chuyển nhóm sang chủ đề khác khi nhóm đã được gán cho ý kiến.', 1;
    END
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_Y_KIEN_GHI_LICH_SU_TAO
ON dbo.Y_KIEN
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.LICH_SU_XU_LY
        (MaYKien, MaNguoiThucHien, HanhDong, GhiChu)
    SELECT
        i.MaYKien,
        i.NguoiGui,
        N'Tiếp nhận ý kiến',
        N'Hệ thống ghi nhận ý kiến mới.'
    FROM inserted i;
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_Y_KIEN_GHI_LICH_SU_CAP_NHAT
ON dbo.Y_KIEN
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.LICH_SU_XU_LY
        (MaYKien, MaNguoiThucHien, HanhDong, GhiChu)
    SELECT
        i.MaYKien,
        COALESCE(i.NguoiRaSoat, i.NguoiGui),
        N'Cập nhật xử lý ý kiến',
        CONCAT(
            N'Trạng thái: ', d.TrangThaiXuLy, N' -> ', i.TrangThaiXuLy,
            N'; nhóm đề xuất: ', COALESCE(CONVERT(NVARCHAR(20), d.MaNhom_AI_DeXuat), N'NULL'),
            N' -> ', COALESCE(CONVERT(NVARCHAR(20), i.MaNhom_AI_DeXuat), N'NULL'),
            N'; nhóm chính thức: ', COALESCE(CONVERT(NVARCHAR(20), d.MaNhom_ChinhThuc), N'NULL'),
            N' -> ', COALESCE(CONVERT(NVARCHAR(20), i.MaNhom_ChinhThuc), N'NULL')
        )
    FROM inserted i
    INNER JOIN deleted d ON d.MaYKien = i.MaYKien
    WHERE ISNULL(i.TrangThaiXuLy, N'') <> ISNULL(d.TrangThaiXuLy, N'')
       OR ISNULL(i.MaNhom_AI_DeXuat, -1) <> ISNULL(d.MaNhom_AI_DeXuat, -1)
       OR ISNULL(i.MaNhom_ChinhThuc, -1) <> ISNULL(d.MaNhom_ChinhThuc, -1);
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_DU_THAO_GHI_LICH_SU_TAO
ON dbo.DU_THAO
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.LICH_SU_XU_LY
        (MaDuThao, MaNguoiThucHien, HanhDong, GhiChu)
    SELECT
        i.MaDuThao,
        i.NguoiCapNhat,
        N'Tạo phiên bản dự thảo',
        CONCAT(
            N'Phiên bản ', i.PhienBan,
            CASE WHEN i.MaDuThao_Truoc IS NULL
                 THEN N' là phiên bản khởi tạo.'
                 ELSE CONCAT(N'; kế thừa từ mã dự thảo ', i.MaDuThao_Truoc, N'.')
            END
        )
    FROM inserted i;
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_LIEN_KET_THAY_DOI_GHI_LICH_SU
ON dbo.LIEN_KET_THAY_DOI
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.LICH_SU_XU_LY
        (MaYKien, MaDuThao, MaNguoiThucHien, HanhDong, GhiChu)
    SELECT
        i.MaYKien,
        i.MaDuThao_Moi,
        NULL,
        N'Liên kết ý kiến với thay đổi dự thảo',
        CONCAT(
            N'Mã liên kết: ', i.MaLienKet,
            N'; mã phản hồi: ', COALESCE(CONVERT(NVARCHAR(20), i.MaPhanHoi), N'Chưa liên kết'),
            CASE WHEN i.NoiDungThayDoi IS NULL THEN N'' ELSE CONCAT(N'; nội dung thay đổi: ', i.NoiDungThayDoi) END
        )
    FROM inserted i;
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_CONG_BO_KET_QUA_GHI_LICH_SU
ON dbo.CONG_BO_KET_QUA
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.LICH_SU_XU_LY
        (MaDuThao, MaNguoiThucHien, HanhDong, GhiChu)
    SELECT
        i.MaDuThao,
        i.NguoiCongBo,
        N'Công bố kết quả tham vấn',
        CONCAT(
            N'Mã công bố: ', i.MaCongBo,
            N'; mã chủ đề: ', i.MaChuDe,
            N'; thời điểm: ', CONVERT(NVARCHAR(30), i.ThoiDiemCongBo, 126)
        )
    FROM inserted i;
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_Y_KIEN_CHAN_XOA
ON dbo.Y_KIEN
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;
    THROW 51004, N'Không được xóa ý kiến đã tiếp nhận; hãy giữ lại để bảo đảm truy vết.', 1;
END;
GO

CREATE OR ALTER TRIGGER dbo.TR_PHAN_HOI_KIEM_TRA_CHU_DE
ON dbo.PHAN_HOI
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        INNER JOIN dbo.NHOM_Y_KIEN n ON n.MaNhom = i.MaNhom
        INNER JOIN dbo.Y_KIEN y ON y.MaYKien = i.MaYKien
        WHERE i.MaNhom IS NOT NULL
          AND i.MaYKien IS NOT NULL
          AND n.MaChuDe <> y.MaChuDe
    )
    BEGIN
        THROW 51005, N'Phản hồi không thể đồng thời gắn với ý kiến và nhóm; nhóm phải thuộc đúng chủ đề của ý kiến.', 1;
    END
END;
GO


CREATE OR ALTER TRIGGER dbo.TR_CONG_BO_KIEM_TRA_CHU_DE
ON dbo.CONG_BO_KET_QUA
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        INNER JOIN dbo.DU_THAO d ON d.MaDuThao = i.MaDuThao
        WHERE d.MaChuDe <> i.MaChuDe
    )
    BEGIN
        THROW 51006, N'Phiên bản dự thảo được công bố phải thuộc đúng chủ đề tham vấn.', 1;
    END
END;
GO
