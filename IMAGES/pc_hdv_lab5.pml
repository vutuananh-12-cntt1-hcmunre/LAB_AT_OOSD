@startuml
autonumber
skinparam responseMessageBelowArrow true

actor "NV Điều hành" as User
boundary "FrmPhanCongHDV" as GUI
control "PhanCongService" as Svc
entity "ChuyenLe" as CL
entity "HuongDanVien" as HDV
database "SQL Server" as DB

User -> GUI : Chọn HDV, loại (LE/DOAN), đối tượng, nhập thù lao
User -> GUI : Nhấn "Phân công"
activate GUI

GUI -> Svc : PhanCong(maPC, maHDV, loai, maDoiTuong, thuLao)
activate Svc

alt loai == 'LE' (Khách lẻ)
    Svc -> DB : SELECT COUNT(*) FROM PhanCongHDV WHERE MaChuyen = @ma
    DB --> Svc : countPC
    alt countPC > 0
        Svc --> GUI : KetQuaXuLy.Fail("Mỗi chuyến khách lẻ chỉ có 1 HDV")
    end
    Svc -> DB : Lấy NgayDi, NgayVe của ChuyenLe
    DB --> Svc : (bd, kt)
else loai == 'DOAN' (Khách đoàn)
    Svc -> DB : Lấy NgayDi, NgayKetThucDuKien của DangKyDoan
    DB --> Svc : (bd, kt)
end

' Kiem tra trung lich
Svc -> DB : SELECT COUNT(*) FROM PhanCongHDV\nWHERE MaHDV = @h AND NgayBatDau <= @kt AND NgayKetThuc >= @bd
DB --> Svc : trungLich

alt trungLich > 0
    Svc --> GUI : KetQuaXuLy.Fail("HDV bị chồng chéo lịch công tác")
else Hợp lệ
    Svc -> DB : INSERT INTO PhanCongHDV(...)
    DB --> Svc : 1 row affected
    Svc --> GUI : KetQuaXuLy.Ok("Đã phân công hướng dẫn viên thành công")
end

deactivate Svc
GUI --> User : Hiển thị thông báo & nạp lại danh sách
deactivate GUI
@enduml