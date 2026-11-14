import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Home() {
  const { user, isCanBo } = useAuth();

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>Trang chủ</h1>
          <p className="muted mt-1">
            Hệ thống tham vấn chính sách — thu thập ý kiến, phân nhóm, phản hồi và công bố thay đổi dự thảo
          </p>
        </div>
      </div>

      <div className="card">
        <h2 className="card-title">Giới thiệu</h2>
        <p style={{ marginTop: '0.75rem' }}>
          Hệ thống hỗ trợ cơ quan nhà nước tổ chức lấy ý kiến nhân dân về các dự thảo
          chính sách. Mọi ý kiến được tiếp nhận, phân nhóm có rà soát bởi cán bộ,
          phản hồi có truy vết và công bố kết quả tiếp thu cùng phiên bản dự thảo.
        </p>
        <ul style={{ margin: '1rem 0 0 1.25rem', lineHeight: 1.8 }}>
          <li>Người dân gửi ý kiến theo từng chủ đề tham vấn</li>
          <li>Phân nhóm ý kiến do cán bộ rà soát xác nhận (không tự động loại ý kiến)</li>
          <li>Cơ quan phản hồi kết quả xử lý theo từng ý kiến hoặc theo nhóm</li>
          <li>Công bố kết quả và phiên bản dự thảo sau tiếp thu, có truy vết</li>
        </ul>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-value">01</div>
          <div className="stat-label">Xem chủ đề đang mở</div>
          <Link to="/chu-de" className="btn btn-outline btn-sm mt-2">Chủ đề</Link>
        </div>
        <div className="stat-card">
          <div className="stat-value">02</div>
          <div className="stat-label">Gửi ý kiến tham vấn</div>
          {user ? (
            <Link to="/y-kien/gui" className="btn btn-outline btn-sm mt-2">Gửi ý kiến</Link>
          ) : (
            <Link to="/login" className="btn btn-outline btn-sm mt-2">Đăng nhập</Link>
          )}
        </div>
        <div className="stat-card">
          <div className="stat-value">03</div>
          <div className="stat-label">Xem kết quả công bố</div>
          <Link to="/cong-bo" className="btn btn-outline btn-sm mt-2">Công bố</Link>
        </div>
        {isCanBo && (
          <div className="stat-card">
            <div className="stat-value">04</div>
            <div className="stat-label">Thống kê & quản lý</div>
            <Link to="/thong-ke" className="btn btn-outline btn-sm mt-2">Thống kê</Link>
          </div>
        )}
      </div>

      {user && (
        <div className="alert alert-info">
          Xin chào, <strong>{user.hoTen}</strong> — Vai trò: {user.tenVaiTro || user.maVaiTro}
          {user.tenCoQuan ? ` · Cơ quan: ${user.tenCoQuan}` : ''}
        </div>
      )}
    </div>
  );
}
