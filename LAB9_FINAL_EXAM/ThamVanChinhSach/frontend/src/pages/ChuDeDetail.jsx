import { useEffect, useState } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

export default function ChuDeDetail() {
  const { id } = useParams();
  const { isCanBo, user } = useAuth();
  const navigate = useNavigate();
  const [chuDe, setChuDe] = useState(null);
  const [nhom, setNhom] = useState([]);
  const [duThao, setDuThao] = useState([]);
  const [error, setError] = useState('');
  const [msg, setMsg] = useState('');
  const [trangThai, setTrangThai] = useState('');
  const [showNhomForm, setShowNhomForm] = useState(false);
  const [nhomForm, setNhomForm] = useState({ tenNhom: '', moTa: '' });

  const load = () => {
    api.get(`/chude/${id}`).then((r) => {
      setChuDe(r.data);
      setTrangThai(r.data.trangThai || '');
    }).catch((e) => setError(e.response?.data || 'Không tải được chủ đề'));
    api.get(`/nhom-ykien/theo-chude/${id}`).then((r) => setNhom(r.data || [])).catch(() => {});
    api.get(`/duthao/theo-chude/${id}`).then((r) => setDuThao(r.data || [])).catch(() => {});
  };

  useEffect(() => { load(); }, [id]);

  const formatDate = (d) => d ? new Date(d).toLocaleString('vi-VN') : '—';

  const doiTrangThai = async () => {
    setMsg('');
    try {
      const res = await api.put(`/chude/${id}/trang-thai`, { trangThai });
      setMsg(res.data);
      load();
    } catch (e) {
      setMsg(e.response?.data || 'Lỗi cập nhật trạng thái');
    }
  };

  const taoNhom = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      const res = await api.post('/nhom-ykien/tao-moi', {
        maChuDe: Number(id),
        tenNhom: nhomForm.tenNhom,
        moTa: nhomForm.moTa
      });
      setMsg(res.data);
      setNhomForm({ tenNhom: '', moTa: '' });
      setShowNhomForm(false);
      load();
    } catch (e) {
      setMsg(e.response?.data || 'Lỗi tạo nhóm');
    }
  };

  if (error) return <div className="alert alert-error">{String(error)}</div>;
  if (!chuDe) return <div className="card">Đang tải...</div>;

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>{chuDe.tieuDe}</h1>
          <p className="muted mt-1">Mã chủ đề: {chuDe.maChuDe}</p>
        </div>
        <div className="actions">
          {user && chuDe.trangThai === 'Đang mở' && (
            <Link to={`/y-kien/gui?chuDe=${id}`} className="btn btn-primary">Gửi ý kiến</Link>
          )}
          <Link to="/chu-de" className="btn btn-outline">Quay lại</Link>
        </div>
      </div>

      {msg && <div className={`alert ${String(msg).startsWith('Lỗi') ? 'alert-error' : 'alert-success'}`}>{String(msg)}</div>}

      <div className="card">
        <div className="detail-row"><span className="label">Trạng thái</span><span className="badge">{chuDe.trangThai}</span></div>
        <div className="detail-row"><span className="label">Bắt đầu</span><span>{formatDate(chuDe.thoiGianBatDau)}</span></div>
        <div className="detail-row"><span className="label">Kết thúc</span><span>{formatDate(chuDe.thoiGianKetThuc)}</span></div>
        <div className="detail-row"><span className="label">Mô tả</span><span style={{ whiteSpace: 'pre-wrap' }}>{chuDe.noiDungMoTa || '—'}</span></div>
      </div>

      {isCanBo && (
        <div className="card">
          <div className="card-header">
            <h3 className="card-title">Cập nhật trạng thái</h3>
          </div>
          <div className="flex gap-1 align-center flex-wrap">
            <select className="form-control" style={{ maxWidth: 220 }} value={trangThai} onChange={(e) => setTrangThai(e.target.value)}>
              <option value="Bản nháp">Bản nháp</option>
              <option value="Đang mở">Đang mở</option>
              <option value="Đã đóng">Đã đóng</option>
              <option value="Đã công bố">Đã công bố</option>
            </select>
            <button type="button" className="btn btn-primary" onClick={doiTrangThai}>Lưu trạng thái</button>
          </div>
        </div>
      )}

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Nhóm ý kiến ({nhom.length})</h3>
          {isCanBo && (
            <button type="button" className="btn btn-outline btn-sm" onClick={() => setShowNhomForm(!showNhomForm)}>
              {showNhomForm ? 'Đóng' : 'Thêm nhóm'}
            </button>
          )}
        </div>
        {showNhomForm && (
          <form onSubmit={taoNhom} className="mb-2">
            <div className="form-group">
              <label className="form-label">Tên nhóm</label>
              <input className="form-control" value={nhomForm.tenNhom} onChange={(e) => setNhomForm({ ...nhomForm, tenNhom: e.target.value })} required />
            </div>
            <div className="form-group">
              <label className="form-label">Mô tả</label>
              <textarea className="form-control" value={nhomForm.moTa} onChange={(e) => setNhomForm({ ...nhomForm, moTa: e.target.value })} />
            </div>
            <button type="submit" className="btn btn-primary btn-sm">Tạo nhóm</button>
          </form>
        )}
        {nhom.length === 0 ? (
          <p className="muted">Chưa có nhóm ý kiến.</p>
        ) : (
          <div className="table-wrap">
            <table className="data-table">
              <thead>
                <tr><th>Mã</th><th>Tên nhóm</th><th>Mô tả</th><th>Số YK liên quan</th></tr>
              </thead>
              <tbody>
                {nhom.map((n) => (
                  <tr key={n.maNhom}>
                    <td>{n.maNhom}</td>
                    <td>{n.tenNhom}</td>
                    <td className="muted">{n.moTa || '—'}</td>
                    <td>{n.soYKien}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      <div className="card">
        <div className="card-header">
          <h3 className="card-title">Dự thảo ({duThao.length})</h3>
          {isCanBo && (
            <Link to={`/du-thao/tao?chuDe=${id}`} className="btn btn-outline btn-sm">Tạo dự thảo</Link>
          )}
        </div>
        {duThao.length === 0 ? (
          <p className="muted">Chưa có phiên bản dự thảo.</p>
        ) : (
          <div className="table-wrap">
            <table className="data-table">
              <thead>
                <tr><th>Mã</th><th>Tên</th><th>Phiên bản</th><th>Trạng thái</th><th></th></tr>
              </thead>
              <tbody>
                {duThao.map((d) => (
                  <tr key={d.maDuThao}>
                    <td>{d.maDuThao}</td>
                    <td>{d.tenDuThao}</td>
                    <td>{d.phienBan}</td>
                    <td><span className="badge">{d.trangThai}</span></td>
                    <td><Link to={`/du-thao/${d.maDuThao}`} className="btn btn-outline btn-sm">Xem</Link></td>
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
