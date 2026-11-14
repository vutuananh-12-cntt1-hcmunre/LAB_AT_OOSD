import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

export default function CongBo() {
  const { isCanBo } = useAuth();
  const [list, setList] = useState([]);
  const [chuDeList, setChuDeList] = useState([]);
  const [duThaoList, setDuThaoList] = useState([]);
  const [error, setError] = useState('');
  const [msg, setMsg] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({ maChuDe: '', maDuThao: '', noiDungCongBo: '' });
  const [loading, setLoading] = useState(true);
  const [loadingDuThao, setLoadingDuThao] = useState(false);

  const load = () => {
    api.get('/cong-bo/danh-sach')
      .then((r) => setList(r.data || []))
      .catch((e) => setError(e.response?.data || 'Không tải được danh sách công bố'))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    load();
    api.get('/chude/danh-sach')
      .then((r) => setChuDeList(r.data || []))
      .catch(() => {});
  }, []);

  // Khi chọn chủ đề → load danh sách dự thảo của chủ đề đó
  useEffect(() => {
    if (!form.maChuDe) {
      setDuThaoList([]);
      setForm((f) => ({ ...f, maDuThao: '' }));
      return;
    }
    setLoadingDuThao(true);
    api.get(`/duthao/theo-chude/${form.maChuDe}`)
      .then((r) => {
        setDuThaoList(r.data || []);
        setForm((f) => ({ ...f, maDuThao: '' }));
      })
      .catch(() => setDuThaoList([]))
      .finally(() => setLoadingDuThao(false));
  }, [form.maChuDe]);

  const formatDate = (d) => (d ? new Date(d).toLocaleString('vi-VN') : '—');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg('');
    if (!form.maChuDe || !form.maDuThao) {
      setMsg('Lỗi: Vui lòng chọn chủ đề và dự thảo');
      return;
    }
    try {
      const res = await api.post('/cong-bo/tao-moi', {
        maChuDe: Number(form.maChuDe),
        maDuThao: Number(form.maDuThao),
        noiDungCongBo: form.noiDungCongBo
      });
      setMsg(res.data);
      setShowForm(false);
      setForm({ maChuDe: '', maDuThao: '', noiDungCongBo: '' });
      setDuThaoList([]);
      load();
    } catch (err) {
      setMsg(err.response?.data || 'Lỗi công bố');
    }
  };

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>Công bố kết quả</h1>
          <p className="muted mt-1">Kết quả tiếp thu và phiên bản dự thảo sau tham vấn</p>
        </div>
        {isCanBo && (
          <button type="button" className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
            {showForm ? 'Đóng form' : 'Tạo công bố'}
          </button>
        )}
      </div>

      {error && <div className="alert alert-error">{String(error)}</div>}
      {msg && (
        <div className={`alert ${String(msg).startsWith('Lỗi') ? 'alert-error' : 'alert-success'}`}>
          {String(msg)}
        </div>
      )}

      {showForm && isCanBo && (
        <div className="card">
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label className="form-label">Chủ đề</label>
                <select
                  className="form-control"
                  value={form.maChuDe}
                  onChange={(e) => setForm({ ...form, maChuDe: e.target.value })}
                  required
                >
                  <option value="">— Chọn chủ đề —</option>
                  {chuDeList.map((cd) => (
                    <option key={cd.maChuDe} value={cd.maChuDe}>
                      [{cd.maChuDe}] {cd.tieuDe} ({cd.trangThai})
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label className="form-label">Dự thảo</label>
                <select
                  className="form-control"
                  value={form.maDuThao}
                  onChange={(e) => setForm({ ...form, maDuThao: e.target.value })}
                  required
                  disabled={!form.maChuDe || loadingDuThao}
                >
                  <option value="">
                    {!form.maChuDe
                      ? '— Chọn chủ đề trước —'
                      : loadingDuThao
                        ? 'Đang tải...'
                        : duThaoList.length === 0
                          ? '— Chưa có dự thảo —'
                          : '— Chọn dự thảo —'}
                  </option>
                  {duThaoList.map((dt) => (
                    <option key={dt.maDuThao} value={dt.maDuThao}>
                      [{dt.maDuThao}] {dt.tenDuThao} — v{dt.phienBan} ({dt.trangThai})
                    </option>
                  ))}
                </select>
              </div>
            </div>
            <div className="form-group">
              <label className="form-label">Nội dung công bố</label>
              <textarea
                className="form-control"
                value={form.noiDungCongBo}
                onChange={(e) => setForm({ ...form, noiDungCongBo: e.target.value })}
                required
                rows={5}
                placeholder="Tóm tắt kết quả tiếp thu ý kiến và nội dung thay đổi dự thảo..."
              />
            </div>
            <button type="submit" className="btn btn-primary">Công bố</button>
          </form>
        </div>
      )}

      {loading && <div className="card">Đang tải...</div>}
      {!loading && list.length === 0 && (
        <div className="empty-state">Chưa có công bố kết quả nào.</div>
      )}

      {!loading &&
        list.map((cb) => (
          <div className="card" key={cb.maCongBo}>
            <div className="card-header">
              <h3 className="card-title">{cb.tenChuDe || `Chủ đề #${cb.maChuDe}`}</h3>
              <span className="muted">{formatDate(cb.thoiDiemCongBo)}</span>
            </div>
            <div className="detail-row">
              <span className="label">Dự thảo</span>
              <span>
                {cb.tenDuThao} (v{cb.phienBan}){' '}
                {cb.maDuThao && (
                  <Link to={`/du-thao/${cb.maDuThao}`} className="btn btn-outline btn-sm" style={{ marginLeft: 8 }}>
                    Xem truy vết →
                  </Link>
                )}
              </span>
            </div>
            <div className="detail-row">
              <span className="label">Nội dung</span>
              <span style={{ whiteSpace: 'pre-wrap' }}>{cb.noiDungCongBo}</span>
            </div>
          </div>
        ))}
    </div>
  );
}