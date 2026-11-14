import { useEffect, useState } from 'react';
import api from '../api/axios';

export default function ThongKe() {
  const [data, setData] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get('/thong-ke/chung')
      .then((r) => setData(r.data))
      .catch((e) => setError(e.response?.data || 'Không tải được thống kê'))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>Thống kê ý kiến</h1>
          <p className="muted mt-1">Tổng quan tình hình tiếp nhận và xử lý</p>
        </div>
      </div>

      {error && <div className="alert alert-error">{String(error)}</div>}
      {loading && <div className="card">Đang tải...</div>}

      {data && (
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-value">{data.tongSoYKien ?? 0}</div>
            <div className="stat-label">Tổng số ý kiến</div>
          </div>
          <div className="stat-card">
            <div className="stat-value">{data.soYKienMoi ?? 0}</div>
            <div className="stat-label">Ý kiến mới</div>
          </div>
          <div className="stat-card">
            <div className="stat-value">{data.soYKienDangXuLy ?? 0}</div>
            <div className="stat-label">Đang xử lý</div>
          </div>
          <div className="stat-card">
            <div className="stat-value">{data.soYKienDaPhanHoi ?? 0}</div>
            <div className="stat-label">Đã phản hồi</div>
          </div>
        </div>
      )}
    </div>
  );
}
