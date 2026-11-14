EXEC sp_helptext 'dbo.sp_DangKyNguoiDung';
EXEC sp_helptext 'dbo.sp_DangKyNguoiDung';
EXEC sp_helptext 'dbo.sp_DangKyNguoiDung';
EXEC sp_helptext 'dbo.sp_DangKyNguoiDung';
EXEC sp_helptext 'dbo.sp_DangKyNguoiDung';
EXEC sp_helptext 'dbo.sp_DangKyNguoiDung';
SELECT COUNT(*) AS TongSoTrigger
FROM sys.triggers;
SELECT COUNT(*) AS TongSoProc
FROM sys.procedures;

SELECT 
    name AS TenProcedure,
    create_date AS NgayTao,
    modify_date AS NgayCapNhatGanNhat
FROM sys.procedures
ORDER BY name ASC;

SELECT 
    name AS TenTrigger,
    type_desc AS LoaiTrigger,
    create_date AS NgayTao,
    is_disabled AS DaTat
FROM sys.triggers

select * from sys.triggers
