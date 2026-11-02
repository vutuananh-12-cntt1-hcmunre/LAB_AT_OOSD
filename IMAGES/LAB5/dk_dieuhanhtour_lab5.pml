@startuml
skinparam packageStyle rectangle
skinparam actorStyle awesome

actor "NV Bán vé" as NVBV
actor "NV Kinh doanh" as NVKD
actor "NV Điều hành" as NVDH

rectangle "Phân Hệ Đăng Ký & Điều Hành Tour" {
  ' Luong khach le
  usecase "Đăng ký vé khách lẻ\n(< 12 người)" as UC_DKLe
  usecase "Chọn chuyến mở bán" as UC_ChonChuyen
  usecase "Thanh toán tiền vé ngay" as UC_ThanhToanVe
  
  UC_DKLe ..> UC_ChonChuyen : <<include>>
  UC_DKLe ..> UC_ThanhToanVe : <<include>>
  NVBV --> UC_DKLe

  ' Luong khach doan
  usecase "Lập phiếu đăng ký đoàn\n(> 12 người)" as UC_DKDoan
  usecase "Đặt cọc trước" as UC_DatCoc
  usecase "Nhập DS người đi bảo hiểm" as UC_DSBaoHiem
  usecase "Hủy đăng ký (mất cọc)" as UC_HuyDoan
  
  UC_DKDoan ..> UC_DatCoc : <<include>>
  UC_DKDoan <.. UC_DSBaoHiem : <<extend>>\n[mua bảo hiểm]
  UC_DKDoan <.. UC_HuyDoan : <<extend>>\n[đoàn báo hủy]
  NVKD --> UC_DKDoan
  NVKD --> UC_HuyDoan

  ' Phan cong dieu hanh
  usecase "Phân công HDV" as UC_PhanCong
  usecase "Kiểm tra trùng lịch" as UC_CheckLich
  
  UC_PhanCong ..> UC_CheckLich : <<include>>
  NVDH --> UC_PhanCong
}
@enduml