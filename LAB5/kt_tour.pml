@startuml
start

:Tour kết thúc và trở về TP.HCM;

if (Loại hình khách tham quan?) then ([Khách đoàn])
  :Kiểm tra thời điểm thanh toán;
  if (Ngày hiện tại > Ngày kết thúc dự kiến?) then ([Đúng])
    :Tính số tiền còn lại\n(Tổng dự kiến - Tiền cọc - Đã trả);
    :Kế toán lập phiếu thu số tiền còn thiếu;
    if (Đã thanh toán đủ toàn bộ?) then ([Đủ])
      :Cập nhật trạng thái 'Đã hoàn tất thanh toán';
    else ([Trả một phần])
      :Ghi nhận thanh toán đợt & lưu dư nợ;
    endif
  else ([Trước khi kết thúc])
    :Từ chối thu tiền (Chỉ thanh toán sau tour);
    stop
  endif
else ([Khách lẻ])
  :Khách lẻ đã hoàn tất tiền vé từ trước;
endif

:NV Chăm sóc KH lập phiếu khảo sát;
:Gửi phiếu khảo sát dịch vụ đến đại diện đoàn / khách lẻ;

if (Khách hàng có phản hồi?) then ([Có gửi đánh giá])
  :Tiếp nhận điểm số đánh giá (1 - 5 sao);
  :Ghi nhận ý kiến đóng góp vào hệ thống;
else ([Không phản hồi])
  :Lưu trạng thái đã gửi;
endif

stop
@enduml