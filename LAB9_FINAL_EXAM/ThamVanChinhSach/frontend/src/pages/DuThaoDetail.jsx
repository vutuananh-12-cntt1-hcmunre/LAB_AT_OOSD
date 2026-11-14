import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

export default function DuThaoDetail() {
  const { id } = useParams();
  const { isCanBo } = useAuth();
  const [data, setData] = useState(null);
  const [ykienLienKet, setYkienLienKet] = useState([]);
  const [error, setError] = useState('');
  const [msg, setMsg] = useState('');

  // Form tạo liên kết thay đổi (cán bộ gắn ý kiến làm cơ sở sửa dự thảo)
  const [showLienKetForm, setShowLienKetForm] = useState(false);
  const [lienKetForm, setLienKetForm] = useState({ maYKien: '', noiDungThayDoi: '' });

  const load = () => {
    api.get(`/duthao/${id}`)
      .then((r) => setData(r.data))
      .catch((e) => setError(e.response?.data || 'Không tải được dự thảo'));
    // Tải ý kiến làm căn cứ sửa đổi phiên bản này (truy vết 2 chiều)
    api.get(`/duthao/${id}/y-kien-lien-ket`)
      .then((r) => setYkienLienKet(r.data || []))
      .catch(() => {});
  };

  useEffect(() => { load(); }, [id]);

  const congBo = async () => {
    setMsg('');
    try {
      const res = await api.put(`/duthao/${id}/cong-bo`);
      setMsg(res.data);
      load();
    } catch (e) {
      setMsg(e.response?.data || 'Lỗi công bố');
    }
  };

  const taoLienKet = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      const res = await api.post(`/duthao/${id}/lien-ket-thay-doi`, {
        maDuThaoMoi: Number(id),
        maYKien: Number(lienKetForm.maYKien),
        noiDungThayDoi: lienKetForm.noiDungThayDoi,
      });
      setMsg(res.data);
      setLienKetForm({ maYKien: '', noiDungThayDoi: '' });
      setShowLienKetForm(false);
      load();
    } catch (e) {
      setMsg(e.response?.data || 'Lỗi tạo liên kết');
    }
  };

  if (error) return <div className="alert alert-error">{String(error)}</div>;
  if (!data) return <div className="card">Đang tải...</div>;

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>{data.tenDuThao}</h1>
          <p className="muted mt-1">Phiên bản {data.phienBan} · Chủ đề #{data.maChuDe}</p>
        </div>
        <div className="actions">
          {isCanBo && data.trangThai !== 'Đã công bố' && (
            <button type="button" className="btn btn-primary" onClick={congBo}>Công bố dự thảo</button>
          )}
          <Link to={`/chu-de/${data.maChuDe}`} className="btn btn-outline">Về chủ đề</Link>
        </div>
      </div>

      {msg && (
        <div className={`alert ${String(msg).startsWith('Lỗi') ? 'alert-error' : 'alert-success'}`}>
          {String(msg)}
        </div>
      )}

      {/* ── Thông tin dự thảo ── */}
      <div className="card">
        <div className="detail-row"><span className="label">Trạng thái</span><span className="badge">{data.trangThai}</span></div>
        <div className="detail-row"><span className="label">Thời điểm tạo</span><span>{data.thoiDiemTao ? new Date(data.thoiDiemTao).toLocaleString('vi-VN') : '—'}</span></div>
        {data.ghiChuThayDoi && (
          <div className="detail-row"><span className="label">Ghi chú thay đổi</span><span>{data.ghiChuThayDoi}</span></div>
        )}
        <div className="detail-row"><span className="label">Nội dung</span><span style={{ whiteSpace: 'pre-wrap' }}>{data.noiDung}</span></div>
      </div>

      {/* ── Truy vết: Ý kiến làm căn cứ sửa đổi phiên bản này ── */}
      <div className="card">
        <div className="card-header">
          <h3 className="card-title">🔗 Truy vết — Ý kiến đóng góp làm cơ sở cho phiên bản này</h3>
          {isCanBo && (
            <button
              type="button"
              className="btn btn-outline btn-sm"
              onClick={() => setShowLienKetForm(!showLienKetForm)}
            >
              {showLienKetForm ? 'Đóng' : '+ Thêm liên kết ý kiến'}
            </button>
          )}
        </div>

        {/* Form tạo liên kết thay đổi */}
        {showLienKetForm && isCanBo && (
          <form onSubmit={taoLienKet} style={{
            background: 'var(--view-card, #f8fafc)',
            border: '1px solid #e2e8f0',
            borderRadius: 8,
            padding: '1rem',
            marginBottom: '0.85rem'
          }}>
            <p style={{ fontWeight: 600, marginBottom: '0.75rem', fontSize: '0.9rem' }}>
              Gắn ý kiến làm căn cứ sửa đổi dự thảo này:
            </p>
            <div className="form-group">
              <label className="form-label">Mã ý kiến (số)</label>
              <input
                className="form-control"
                type="number"
                min="1"
                value={lienKetForm.maYKien}
                onChange={(e) => setLienKetForm({ ...lienKetForm, maYKien: e.target.value })}
                placeholder="Nhập mã ý kiến..."
                required
                style={{ maxWidth: 200 }}
              />
            </div>
            <div className="form-group">
              <label className="form-label">Nội dung thay đổi cụ thể</label>
              <textarea
                className="form-control"
                value={lienKetForm.noiDungThayDoi}
                onChange={(e) => setLienKetForm({ ...lienKetForm, noiDungThayDoi: e.target.value })}
                placeholder="Mô tả nội dung đã thay đổi dựa trên ý kiến này..."
                rows={3}
                required
              />
            </div>
            <div style={{ display: 'flex', gap: '0.5rem' }}>
              <button type="submit" className="btn btn-primary btn-sm">Lưu liên kết</button>
              <button type="button" className="btn btn-outline btn-sm" onClick={() => setShowLienKetForm(false)}>Huỷ</button>
            </div>
          </form>
        )}

        {/* Bảng danh sách ý kiến liên kết */}
        {ykienLienKet.length === 0 ? (
          <p className="muted" style={{ marginTop: '0.5rem' }}>
            Chưa có ý kiến nào được gắn làm căn cứ sửa đổi phiên bản này.
          </p>
        ) : (
          <div className="table-wrap mt-1">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Mã YK</th>
                  <th>Nội dung ý kiến</th>
                  <th>Trạng thái ý kiến</th>
                  <th>Nội dung thay đổi</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {ykienLienKet.map((yk) => (
                  <tr key={yk.maYKien}>
                    <td>{yk.maYKien}</td>
                    <td style={{ maxWidth: 260, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                      {yk.noiDung}
                    </td>
                    <td className="muted">{yk.tenNhomChinhThuc || '—'}</td>
                    <td className="muted">{yk.noiDungThayDoi || '—'}</td>
                    <td>
                      <Link to={`/y-kien/${yk.maYKien}`} className="btn btn-outline btn-sm">Xem YK</Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
