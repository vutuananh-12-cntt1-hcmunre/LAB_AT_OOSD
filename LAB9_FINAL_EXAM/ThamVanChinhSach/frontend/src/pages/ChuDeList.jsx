import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

function statusBadge(tt) {
  if (tt === 'Đang mở') return 'badge badge-open';
  if (tt === 'Đã đóng') return 'badge badge-closed';
  if (tt === 'Bản nháp') return 'badge badge-draft';
  if (tt === 'Đã công bố') return 'badge badge-done';
  return 'badge';
}

export default function ChuDeList() {
  const { isCanBo } = useAuth();
  const [list, setList] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get('/chude/danh-sach')
      .then((res) => setList(res.data || []))
      .catch((err) => setError(err.response?.data || 'Không tải được danh sách chủ đề'))
      .finally(() => setLoading(false));
  }, []);

  const formatDate = (d) => {
    if (!d) return '—';
    try {
      return new Date(d).toLocaleString('vi-VN');
    } catch {
      return d;
    }
  };

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>Chủ đề tham vấn</h1>
          <p className="muted mt-1">Danh sách các đợt lấy ý kiến chính sách</p>
        </div>
        {isCanBo && (
          <Link to="/chu-de/tao" className="btn btn-primary">Tạo chủ đề mới</Link>
        )}
      </div>

      {error && <div className="alert alert-error">{String(error)}</div>}
      {loading && <div className="card">Đang tải...</div>}

      {!loading && list.length === 0 && (
        <div className="empty-state">Chưa có chủ đề tham vấn nào.</div>
      )}

      {!loading && list.length > 0 && (
        <div className="card" style={{ padding: 0 }}>
          <div className="table-wrap">
            <table className="data-table">
              <thead>
                <tr>
                  <th style={{ width: 60 }}>Mã</th>
                  <th>Tiêu đề</th>
                  <th>Thời gian</th>
                  <th>Trạng thái</th>
                  <th style={{ width: 100 }}></th>
                </tr>
              </thead>
              <tbody>
                {list.map((cd) => (
                  <tr key={cd.maChuDe}>
                    <td>{cd.maChuDe}</td>
                    <td>
                      <strong>{cd.tieuDe}</strong>
                      {cd.noiDungMoTa && (
                        <div className="muted" style={{ marginTop: 4, maxWidth: 420 }}>
                          {cd.noiDungMoTa.length > 120 ? cd.noiDungMoTa.slice(0, 120) + '…' : cd.noiDungMoTa}
                        </div>
                      )}
                    </td>
                    <td className="muted" style={{ whiteSpace: 'nowrap' }}>
                      {formatDate(cd.thoiGianBatDau)}
                      <br />→ {formatDate(cd.thoiGianKetThuc)}
                    </td>
                    <td><span className={statusBadge(cd.trangThai)}>{cd.trangThai}</span></td>
                    <td>
                      <Link to={`/chu-de/${cd.maChuDe}`} className="btn btn-outline btn-sm">Chi tiết</Link>
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
