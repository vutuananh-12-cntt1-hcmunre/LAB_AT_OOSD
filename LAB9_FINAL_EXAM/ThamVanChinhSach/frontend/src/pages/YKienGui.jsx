import { useEffect, useState } from 'react';
import { useNavigate, useSearchParams, Link } from 'react-router-dom';
import api from '../api/axios';

export default function YKienGui() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const [chuDeList, setChuDeList] = useState([]);
  const [maChuDe, setMaChuDe] = useState(searchParams.get('chuDe') || '');
  const [noiDung, setNoiDung] = useState('');
  const [file, setFile] = useState(null);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    api.get('/chude/danh-sach').then((r) => {
      const open = (r.data || []).filter((c) => c.trangThai === 'Đang mở');
      setChuDeList(open.length ? open : r.data || []);
    }).catch(() => {});
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);
    try {
      const res = await api.post('/ykien/gui', {
        maChuDe: Number(maChuDe),
        noiDung
      });
      const msg = res.data || '';
      setSuccess(msg);
      // Extract ma y kien if possible for upload
      const match = String(msg).match(/Mã ý kiến mới:\s*(\d+)/i);
      if (file && match) {
        const maYKien = match[1];
        const fd = new FormData();
        fd.append('file', file);
        await api.post(`/tailieu/upload/${maYKien}`, fd, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
      }
      setTimeout(() => navigate('/y-kien/cua-toi'), 1200);
    } catch (err) {
      setError(err.response?.data || 'Gửi ý kiến thất bại');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Gửi ý kiến tham vấn</h1>
        <Link to="/y-kien" className="btn btn-outline">Quay lại</Link>
      </div>
      {error && <div className="alert alert-error">{String(error)}</div>}
      {success && <div className="alert alert-success">{String(success)}</div>}
      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Chủ đề</label>
            <select className="form-control" value={maChuDe} onChange={(e) => setMaChuDe(e.target.value)} required>
              <option value="">— Chọn chủ đề —</option>
              {chuDeList.map((c) => (
                <option key={c.maChuDe} value={c.maChuDe}>
                  [{c.maChuDe}] {c.tieuDe} ({c.trangThai})
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label className="form-label">Nội dung ý kiến</label>
            <textarea className="form-control" value={noiDung} onChange={(e) => setNoiDung(e.target.value)} required rows={8} />
          </div>
          <div className="form-group">
            <label className="form-label">Tài liệu đính kèm (tuỳ chọn)</label>
            <input type="file" className="form-control" onChange={(e) => setFile(e.target.files?.[0] || null)} />
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Đang gửi...' : 'Gửi ý kiến'}
          </button>
        </form>
      </div>
    </div>
  );
}
