USE ThamVanChinhSach;
GO

SET NOCOUNT ON;
GO

/* =========================================================
   TẮT TOÀN BỘ TRIGGER trên Y_KIEN (seed dữ liệu lịch sử)
   ========================================================= */
IF OBJECT_ID('dbo.TR_Y_KIEN_CHAN_XOA', 'TR') IS NOT NULL
    DISABLE TRIGGER dbo.TR_Y_KIEN_CHAN_XOA ON dbo.Y_KIEN;
IF OBJECT_ID('dbo.TR_Y_KIEN_KIEM_TRA_THOI_GIAN', 'TR') IS NOT NULL
    DISABLE TRIGGER dbo.TR_Y_KIEN_KIEM_TRA_THOI_GIAN ON dbo.Y_KIEN;
-- Tắt mọi trigger còn lại trên Y_KIEN (nếu có)
DECLARE @sql NVARCHAR(MAX) = N'';
SELECT @sql += N'DISABLE TRIGGER ' + QUOTENAME(name) + N' ON dbo.Y_KIEN; '
FROM sys.triggers
WHERE parent_id = OBJECT_ID('dbo.Y_KIEN') AND is_disabled = 0;
IF LEN(@sql) > 0 EXEC sp_executesql @sql;
GO

/* Tắt kiểm tra FK tạm thời */
ALTER TABLE dbo.LICH_SU_XU_LY NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.LIEN_KET_THAY_DOI NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.CONG_BO_KET_QUA NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.PHAN_HOI NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.TAI_LIEU_Y_KIEN NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.Y_KIEN NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.NHOM_Y_KIEN NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.DU_THAO NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.CHU_DE_THAM_VAN NOCHECK CONSTRAINT ALL;
ALTER TABLE dbo.NGUOI_DUNG NOCHECK CONSTRAINT ALL;
GO

/* =========================================================
   XÓA TOÀN BỘ DỮ LIỆU
   ========================================================= */
DELETE FROM dbo.LICH_SU_XU_LY;
DELETE FROM dbo.LIEN_KET_THAY_DOI;
DELETE FROM dbo.CONG_BO_KET_QUA;
DELETE FROM dbo.PHAN_HOI;
DELETE FROM dbo.TAI_LIEU_Y_KIEN;
DELETE FROM dbo.Y_KIEN;
DELETE FROM dbo.NHOM_Y_KIEN;
DELETE FROM dbo.DU_THAO;
DELETE FROM dbo.CHU_DE_THAM_VAN;
DELETE FROM dbo.NGUOI_DUNG;
DELETE FROM dbo.CO_QUAN;
DELETE FROM dbo.VAI_TRO;
GO

/* Bật lại FK */
ALTER TABLE dbo.LICH_SU_XU_LY CHECK CONSTRAINT ALL;
ALTER TABLE dbo.LIEN_KET_THAY_DOI CHECK CONSTRAINT ALL;
ALTER TABLE dbo.CONG_BO_KET_QUA CHECK CONSTRAINT ALL;
ALTER TABLE dbo.PHAN_HOI CHECK CONSTRAINT ALL;
ALTER TABLE dbo.TAI_LIEU_Y_KIEN CHECK CONSTRAINT ALL;
ALTER TABLE dbo.Y_KIEN CHECK CONSTRAINT ALL;
ALTER TABLE dbo.NHOM_Y_KIEN CHECK CONSTRAINT ALL;
ALTER TABLE dbo.DU_THAO CHECK CONSTRAINT ALL;
ALTER TABLE dbo.CHU_DE_THAM_VAN CHECK CONSTRAINT ALL;
ALTER TABLE dbo.NGUOI_DUNG CHECK CONSTRAINT ALL;
GO

/* Reset identity */
DBCC CHECKIDENT ('dbo.CO_QUAN', RESEED, 0);
DBCC CHECKIDENT ('dbo.NGUOI_DUNG', RESEED, 0);
DBCC CHECKIDENT ('dbo.CHU_DE_THAM_VAN', RESEED, 0);
DBCC CHECKIDENT ('dbo.DU_THAO', RESEED, 0);
DBCC CHECKIDENT ('dbo.NHOM_Y_KIEN', RESEED, 0);
DBCC CHECKIDENT ('dbo.Y_KIEN', RESEED, 0);
DBCC CHECKIDENT ('dbo.TAI_LIEU_Y_KIEN', RESEED, 0);
DBCC CHECKIDENT ('dbo.PHAN_HOI', RESEED, 0);
DBCC CHECKIDENT ('dbo.CONG_BO_KET_QUA', RESEED, 0);
DBCC CHECKIDENT ('dbo.LIEN_KET_THAY_DOI', RESEED, 0);
DBCC CHECKIDENT ('dbo.LICH_SU_XU_LY', RESEED, 0);
GO

/* =========================================================
   1. VAI TRÒ
   ========================================================= */
INSERT INTO dbo.VAI_TRO (MaVaiTro, TenVaiTro) VALUES
('NGUOI_DAN', N'Người dân'),
('RA_SOAT', N'Cán bộ rà soát'),
('CAN_BO_CO_QUAN', N'Cán bộ cơ quan'),
('ADMIN', N'Quản trị viên');
GO

/* =========================================================
   2. CƠ QUAN
   ========================================================= */
INSERT INTO dbo.CO_QUAN (TenCoQuan, EmailLienHe, SoDienThoai, DiaChi, TrangThai) VALUES
(N'Sở Giao thông Vận tải TP. Hồ Chí Minh', 'sgtvt@tphcm.gov.vn', '02838291001', N'63 Lý Tự Trọng, Quận 1, TP.HCM', 1),
(N'Sở Tài nguyên và Môi trường TP.HCM', 'sotnmt@tphcm.gov.vn', '02838291002', N'63 Lý Tự Trọng, Quận 1, TP.HCM', 1),
(N'Bộ Tư pháp', 'botuphap@moj.gov.vn', '02462739718', N'60 Trần Phú, Ba Đình, Hà Nội', 1);
GO

/* =========================================================
   3. NGƯỜI DÙNG  (mật khẩu: 123456)
   ========================================================= */
INSERT INTO dbo.NGUOI_DUNG (HoTen, Email, MatKhau, SoDienThoai, DiaChi, MaVaiTro, MaCoQuan, TrangThai) VALUES
(N'Nguyễn Văn Admin', 'admin@gov.vn', '123456', '0901000001', N'Hà Nội', 'ADMIN', NULL, 1),
(N'Nguyễn Văn Cán Bộ', 'canbo@gov.vn', '123456', '0912345678', N'TP. Hồ Chí Minh', 'CAN_BO_CO_QUAN', 1, 1),
(N'Trần Thị Lan', 'lan.tran@sgtvt.gov.vn', '123456', '0912345679', N'TP. Hồ Chí Minh', 'CAN_BO_CO_QUAN', 1, 1),
(N'Lê Minh Rà Soát', 'rasoat@gmail.com', '123456', '0912345680', N'TP. Hồ Chí Minh', 'RA_SOAT', 1, 1),
(N'Phạm Quốc Hùng', 'hung.pham@gov.vn', '123456', '0912345681', N'TP. Hồ Chí Minh', 'RA_SOAT', 1, 1),
(N'Hoàng Văn An', 'hoangvanan@email.com', '123456', '0987654321', N'Quận 7, TP.HCM', 'NGUOI_DAN', NULL, 1),
(N'Ngô Thị Bình', 'ngothibinh@email.com', '123456', '0987654322', N'Quận Bình Thạnh, TP.HCM', 'NGUOI_DAN', NULL, 1),
(N'Đỗ Văn Cường', 'dovancuong@email.com', '123456', '0987654323', N'Quận 3, TP.HCM', 'NGUOI_DAN', NULL, 1),
(N'Vũ Thị Dung', 'vuthidung@email.com', '123456', '0987654324', N'Thủ Đức, TP.HCM', 'NGUOI_DAN', NULL, 1),
(N'Bùi Minh Đức', 'buiminhduc@email.com', '123456', '0987654325', N'Quận 10, TP.HCM', 'NGUOI_DAN', NULL, 1);
GO

/* =========================================================
   4. CHỦ ĐỀ THAM VẤN
   ========================================================= */
INSERT INTO dbo.CHU_DE_THAM_VAN
    (MaCoQuan, TieuDe, NoiDungMoTa, ThoiGianBatDau, ThoiGianKetThuc, TrangThai, NguoiTao)
VALUES
(1,
 N'Tham vấn dự thảo Nghị định quy định về xử phạt vi phạm hành chính trong lĩnh vực giao thông đường bộ',
 N'Lấy ý kiến nhân dân về khung xử phạt nồng độ cồn, tốc độ, và các hành vi vi phạm phổ biến. Thời gian tiếp nhận ý kiến 30 ngày.',
 DATEADD(DAY, -10, SYSDATETIME()),
 DATEADD(DAY, 20, SYSDATETIME()),
 N'Đang mở',
 2),
(1,
 N'Tham vấn dự thảo Quy chuẩn kỹ thuật quốc gia về báo hiệu đường bộ',
 N'Thu thập ý kiến về hệ thống biển báo, vạch kẻ đường, đèn tín hiệu và phân làn trên đường đô thị và cao tốc.',
 DATEADD(DAY, -5, SYSDATETIME()),
 DATEADD(DAY, 25, SYSDATETIME()),
 N'Đang mở',
 2),
(2,
 N'Tham vấn dự thảo quy định về thu gom và xử lý chất thải rắn sinh hoạt',
 N'Lấy ý kiến về phân loại rác tại nguồn, mức phí thu gom và trách nhiệm của hộ gia đình, doanh nghiệp.',
 DATEADD(DAY, -60, SYSDATETIME()),
 DATEADD(DAY, -15, SYSDATETIME()),
 N'Đã đóng',
 2),
(1,
 N'Tham vấn dự thảo Luật Giao thông đường bộ (sửa đổi) – giai đoạn 2',
 N'Bản nháp nội bộ, chưa mở lấy ý kiến công khai.',
 DATEADD(DAY, 5, SYSDATETIME()),
 DATEADD(DAY, 35, SYSDATETIME()),
 N'Bản nháp',
 2);
GO

/* =========================================================
   5. NHÓM Ý KIẾN
   ========================================================= */
INSERT INTO dbo.NHOM_Y_KIEN (MaChuDe, TenNhom, MoTa) VALUES
(1, N'Nhóm 1: Xử phạt nồng độ cồn', N'Ý kiến về mức phạt tiền, tước GPLX, và khung xử lý theo nồng độ cồn.'),
(1, N'Nhóm 2: Tốc độ và khoảng cách an toàn', N'Góp ý về tốc độ tối đa trên cao tốc, quốc lộ và khoảng cách an toàn giữa các phương tiện.'),
(1, N'Nhóm 3: Hạ tầng và biển báo', N'Ý kiến liên quan đến biển báo, vạch kẻ đường, đèn tín hiệu.'),
(1, N'Nhóm 4: Khác', N'Các ý kiến không thuộc ba nhóm trên.'),
(2, N'Nhóm 1: Biển báo chỉ dẫn', N'Góp ý về hình thức, vị trí đặt biển báo chỉ dẫn.'),
(2, N'Nhóm 2: Vạch kẻ đường', N'Ý kiến về vạch liền, vạch đứt, vạch phân làn.'),
(2, N'Nhóm 3: Đèn tín hiệu', N'Góp ý về chu kỳ đèn, tín hiệu phụ cho người đi bộ.'),
(3, N'Nhóm 1: Phân loại rác tại nguồn', N'Ý kiến về số loại rác cần phân, hướng dẫn thực hiện.'),
(3, N'Nhóm 2: Mức phí thu gom', N'Góp ý về mức phí theo hộ và theo doanh nghiệp.'),
(3, N'Nhóm 3: Trách nhiệm xử lý', N'Ý kiến về trách nhiệm của địa phương và đơn vị thu gom.');
GO

/* =========================================================
   6. DỰ THẢO
   ========================================================= */
INSERT INTO dbo.DU_THAO
    (MaChuDe, TenDuThao, PhienBan, NoiDung, TrangThai, NguoiCapNhat, MaDuThao_Truoc, GhiChuThayDoi)
VALUES
(1,
 N'Dự thảo Nghị định xử phạt VPHC giao thông đường bộ',
 N'1.0',
 N'Điều 1. Phạm vi điều chỉnh...
Điều 5. Xử phạt vi phạm nồng độ cồn:
- Nồng độ cồn vượt quá 50 mg/100 ml máu: phạt tiền từ 6.000.000 đến 8.000.000 đồng.
- Tước quyền sử dụng GPLX từ 10 đến 12 tháng.',
 N'Đã thay thế',
 2, NULL, NULL),
(1,
 N'Dự thảo Nghị định xử phạt VPHC giao thông đường bộ (sau tiếp thu ý kiến)',
 N'2.0',
 N'Điều 1. Phạm vi điều chỉnh...
Điều 5. Xử phạt vi phạm nồng độ cồn (đã điều chỉnh theo ý kiến):
- Nồng độ cồn vượt quá 50 mg/100 ml máu: phạt tiền từ 7.000.000 đến 10.000.000 đồng.
- Tước quyền sử dụng GPLX từ 11 đến 24 tháng đối với tái phạm.
Điều 8. Bổ sung quy định về khoảng cách an toàn tối thiểu trên cao tốc.',
 N'Bản nháp',
 2, 1, N'Tiếp thu ý kiến về tăng mức phạt nồng độ cồn và bổ sung khoảng cách an toàn trên cao tốc.'),
(2,
 N'Dự thảo QCVN về báo hiệu đường bộ',
 N'1.0',
 N'Phần 1. Quy định chung về biển báo.
Phần 2. Vạch kẻ đường.
Phần 3. Đèn tín hiệu giao thông.',
 N'Bản nháp',
 2, NULL, NULL),
(3,
 N'Dự thảo quy định thu gom chất thải rắn sinh hoạt',
 N'1.0',
 N'Chương I. Quy định chung.
Chương II. Phân loại rác tại nguồn (3 nhóm: hữu cơ, tái chế, còn lại).
Chương III. Mức phí thu gom.',
 N'Đã công bố',
 2, NULL, N'Công bố sau đợt tham vấn tháng trước.');
GO

/* =========================================================
   7. Ý KIẾN  (trigger thời gian đã tắt — cho phép seed lịch sử)
   ========================================================= */
INSERT INTO dbo.Y_KIEN
    (MaChuDe, NguoiGui, NoiDung, ThoiDiemGui, MaNhom_ChinhThuc, NguoiRaSoat, ThoiDiemRaSoat, TrangThaiXuLy)
VALUES
(1, 6,
 N'Tôi đề nghị tăng mức phạt nồng độ cồn lên ít nhất 10 triệu đồng với trường hợp vượt ngưỡng nghiêm trọng, đồng thời kéo dài thời gian tước GPLX để răn đe.',
 DATEADD(DAY, -8, SYSDATETIME()), 1, 4, DATEADD(DAY, -6, SYSDATETIME()), N'Đã phản hồi'),
(1, 7,
 N'Nên quy định rõ khoảng cách an toàn tối thiểu giữa các xe trên cao tốc theo từng dải tốc độ, hiện tài xế thường bám đuôi rất nguy hiểm.',
 DATEADD(DAY, -7, SYSDATETIME()), 2, 4, DATEADD(DAY, -5, SYSDATETIME()), N'Đã phản hồi'),
(1, 8,
 N'Nhiều đoạn đường thiếu biển báo tốc độ và vạch kẻ mờ, cần gắn trách nhiệm bảo trì hạ tầng trước khi tăng mức phạt người dân.',
 DATEADD(DAY, -6, SYSDATETIME()), 3, 4, DATEADD(DAY, -4, SYSDATETIME()), N'Đã rà soát'),
(1, 9,
 N'Tôi ủng hộ xử lý nghiêm nồng độ cồn nhưng đề nghị có lộ trình tuyên truyền 3–6 tháng trước khi áp dụng mức phạt mới.',
 DATEADD(DAY, -5, SYSDATETIME()), 1, 4, DATEADD(DAY, -3, SYSDATETIME()), N'Đã rà soát'),
(1, 10,
 N'Đề xuất camera giám sát tự động gắn với hệ thống phạt nguội để giảm tiêu cực khi dừng xe kiểm tra.',
 DATEADD(DAY, -4, SYSDATETIME()), NULL, NULL, NULL, N'Mới tiếp nhận'),
(1, 6,
 N'Khung tốc độ trên quốc lộ đoạn qua khu dân cư nên hạ xuống 50 km/h thay vì 60 km/h như hiện nay.',
 DATEADD(DAY, -3, SYSDATETIME()), NULL, NULL, NULL, N'Mới tiếp nhận'),
(2, 7,
 N'Biển báo chỉ dẫn trên đường cao tốc nên dùng chữ lớn hơn và có đèn phản quang để dễ nhìn ban đêm.',
 DATEADD(DAY, -4, SYSDATETIME()), 5, 5, DATEADD(DAY, -2, SYSDATETIME()), N'Đã rà soát'),
(2, 8,
 N'Vạch kẻ đường tại nhiều nút giao bị mờ, đề nghị quy định chu kỳ sơn lại định kỳ và công khai tiến độ.',
 DATEADD(DAY, -3, SYSDATETIME()), NULL, NULL, NULL, N'Mới tiếp nhận'),
(2, 9,
 N'Đèn tín hiệu cho người đi bộ cần có đồng hồ đếm ngược và âm thanh hỗ trợ người khiếm thị.',
 DATEADD(DAY, -2, SYSDATETIME()), NULL, NULL, NULL, N'Mới tiếp nhận'),
(3, 6,
 N'Nên phân loại rác thành 3 nhóm rõ ràng: hữu cơ, tái chế và còn lại; hướng dẫn bằng hình ảnh tại mỗi thùng rác công cộng.',
 DATEADD(DAY, -40, SYSDATETIME()), 8, 4, DATEADD(DAY, -35, SYSDATETIME()), N'Đã phản hồi'),
(3, 7,
 N'Mức phí thu gom hiện chưa hợp lý với hộ nghèo, đề nghị có mức hỗ trợ hoặc miễn giảm theo hoàn cảnh.',
 DATEADD(DAY, -38, SYSDATETIME()), 9, 4, DATEADD(DAY, -34, SYSDATETIME()), N'Đã phản hồi');
GO

/* =========================================================
   8. PHẢN HỒI
   ========================================================= */
INSERT INTO dbo.PHAN_HOI (MaYKien, MaNhom, KetQuaXuLy, NoiDungGiaiThich, NguoiPhanHoi, ThoiDiemPhanHoi)
VALUES
(1, NULL, N'Tiếp thu',
 N'Cơ quan tiếp thu ý kiến tăng mức phạt và thời gian tước GPLX. Nội dung đã được điều chỉnh tại dự thảo phiên bản 2.0.',
 2, DATEADD(DAY, -5, SYSDATETIME())),
(2, NULL, N'Tiếp thu',
 N'Đã bổ sung quy định khoảng cách an toàn tối thiểu trên cao tốc trong dự thảo phiên bản 2.0.',
 2, DATEADD(DAY, -4, SYSDATETIME())),
(10, NULL, N'Tiếp thu một phần',
 N'Tiếp thu việc phân loại 3 nhóm rác. Việc bắt buộc hình ảnh hướng dẫn sẽ được khuyến nghị thực hiện tại địa phương.',
 2, DATEADD(DAY, -30, SYSDATETIME())),
(11, NULL, N'Tiếp thu',
 N'Sẽ nghiên cứu chính sách miễn giảm phí thu gom cho hộ nghèo, cận nghèo theo quy định của địa phương.',
 2, DATEADD(DAY, -29, SYSDATETIME()));
GO

/* =========================================================
   9. LIÊN KẾT THAY ĐỔI
   ========================================================= */
INSERT INTO dbo.LIEN_KET_THAY_DOI (MaDuThao_Moi, MaYKien, MaPhanHoi, NoiDungThayDoi)
VALUES
(2, 1, 1, N'Điều chỉnh mức phạt nồng độ cồn và thời gian tước GPLX theo ý kiến người dân.'),
(2, 2, 2, N'Bổ sung Điều về khoảng cách an toàn trên cao tốc.');
GO

/* =========================================================
   10. CÔNG BỐ KẾT QUẢ
   ========================================================= */
INSERT INTO dbo.CONG_BO_KET_QUA (MaChuDe, MaDuThao, NguoiCongBo, ThoiDiemCongBo, NoiDungCongBo)
VALUES
(3, 4, 2, DATEADD(DAY, -14, SYSDATETIME()),
 N'Sở TN&MT công bố kết quả tham vấn dự thảo quy định thu gom chất thải rắn sinh hoạt.
Tổng số ý kiến tiếp nhận trong đợt mẫu.
Tiếp thu: phân loại 3 nhóm rác tại nguồn; nghiên cứu miễn giảm phí cho hộ nghèo.
Dự thảo phiên bản 1.0 được công bố kèm theo.');
GO

/* =========================================================
   11. LỊCH SỬ XỬ LÝ
   ========================================================= */
INSERT INTO dbo.LICH_SU_XU_LY (MaYKien, MaDuThao, MaNguoiThucHien, HanhDong, ThoiDiem, GhiChu)
VALUES
(1, NULL, 6, N'Gửi ý kiến', DATEADD(DAY, -8, SYSDATETIME()), N'Người dân gửi ý kiến về nồng độ cồn'),
(1, NULL, 4, N'Rà soát phân nhóm', DATEADD(DAY, -6, SYSDATETIME()), N'Gán nhóm 1: Xử phạt nồng độ cồn'),
(1, NULL, 2, N'Phản hồi ý kiến', DATEADD(DAY, -5, SYSDATETIME()), N'Tiếp thu, cập nhật dự thảo 2.0'),
(2, NULL, 7, N'Gửi ý kiến', DATEADD(DAY, -7, SYSDATETIME()), NULL),
(2, NULL, 4, N'Rà soát phân nhóm', DATEADD(DAY, -5, SYSDATETIME()), N'Gán nhóm 2'),
(2, NULL, 2, N'Phản hồi ý kiến', DATEADD(DAY, -4, SYSDATETIME()), N'Tiếp thu'),
(NULL, 2, 2, N'Tạo phiên bản dự thảo', DATEADD(DAY, -4, SYSDATETIME()), N'Tạo dự thảo 2.0 sau tiếp thu'),
(10, NULL, 6, N'Gửi ý kiến', DATEADD(DAY, -40, SYSDATETIME()), NULL),
(10, NULL, 4, N'Rà soát phân nhóm', DATEADD(DAY, -35, SYSDATETIME()), NULL),
(10, NULL, 2, N'Phản hồi ý kiến', DATEADD(DAY, -30, SYSDATETIME()), NULL),
(NULL, 4, 2, N'Công bố kết quả', DATEADD(DAY, -14, SYSDATETIME()), N'Công bố chủ đề thu gom rác');
GO

/* =========================================================
   BẬT LẠI TOÀN BỘ TRIGGER trên Y_KIEN
   ========================================================= */
DECLARE @sql2 NVARCHAR(MAX) = N'';
SELECT @sql2 += N'ENABLE TRIGGER ' + QUOTENAME(name) + N' ON dbo.Y_KIEN; '
FROM sys.triggers
WHERE parent_id = OBJECT_ID('dbo.Y_KIEN');
IF LEN(@sql2) > 0 EXEC sp_executesql @sql2;
GO

PRINT N'=== Seed data hoàn tất thành công ===';
PRINT N'Tài khoản mẫu (mật khẩu: 123456):';
PRINT N'  admin@gov.vn          - ADMIN';
PRINT N'  canbo@gov.vn          - CAN_BO_CO_QUAN';
PRINT N'  rasoat@gmail.com      - RA_SOAT';
PRINT N'  hoangvanan@email.com  - NGUOI_DAN';
GO
