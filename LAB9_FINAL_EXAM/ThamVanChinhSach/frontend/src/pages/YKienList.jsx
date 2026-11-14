import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';

function statusClass(tt) {
  if (!tt) return 'badge';
  if (tt.includes('Mới')) return 'badge badge-new';
  if (tt.includes('rà soát') || tt.includes('Chờ')) return 'badge badge-review';
  if (tt.includes('phản hồi')) return 'badge badge-done';
  return 'badge';
}

export default function YKienList({ caNhan = false }) {
  const [list, setList] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const url = caNhan ? '/ykien/ca-nhan' : '/ykien/danh-sach';
    api.get(url)
      .then((res) => setList(res.data || []))
      .catch((err) => setError(err.response?.data || 'Không tải được danh sách ý kiến'))
      .finally(() => setLoading(false));
  }, [caNhan]);

  const formatDate = (d) => d ? new Date(d).toLocaleString('vi-VN') : '—';

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>{caNhan ? 'Ý kiến của tôi' : 'Danh sách ý kiến'}</h1>
          <p className="muted mt-1">{caNhan ? 'Các ý kiến bạn đã gửi' : 'Toàn bộ ý kiến trong hệ thống'}</p>
        </div>
        <Link to="/y-kien/gui" className="btn btn-primary">Gửi ý kiến mới</Link>
      </div>

      {error && <div className="alert alert-error">{String(error)}</div>}
      {loading && <div className="card">Đang tải...</div>}

      {!loading && list.length === 0 && (
        <div className="empty-state">Chưa có ý kiến nào.</div>
      )}

      {!loading && list.length > 0 && (
        <div className="card" style={{ padding: 0 }}>
          <div className="table-wrap">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Mã</th>
                  <th>Chủ đề</th>
                  <th>Nội dung</th>
                  <th>Thời điểm</th>
                  <th>Trạng thái</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {list.map((yk) => (
                  <tr key={yk.maYKien}>
                    <td>{yk.maYKien}</td>
                    <td>{yk.maChuDe}</td>
                    <td style={{ maxWidth: 360 }}>
                      {yk.noiDung?.length > 140 ? yk.noiDung.slice(0, 140) + '…' : yk.noiDung}
                    </td>
                    <td className="muted" style={{ whiteSpace: 'nowrap' }}>{formatDate(yk.thoiDiemGui)}</td>
                    <td><span className={statusClass(yk.trangThaiXuLy)}>{yk.trangThaiXuLy}</span></td>
                    <td>
                      <Link to={`/y-kien/${yk.maYKien}`} className="btn btn-outline btn-sm">Chi tiết</Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}
