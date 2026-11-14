import { useState } from 'react';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import api from '../api/axios';

export default function DuThaoCreate() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    maChuDe: searchParams.get('chuDe') || '',
    tenDuThao: '',
    phienBan: '1.0',
    noiDung: '',
    maDuThaoTruoc: '',
    ghiChuThayDoi: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await api.post('/duthao/tao-moi', {
        maChuDe: Number(form.maChuDe),
        tenDuThao: form.tenDuThao,
        phienBan: form.phienBan,
        noiDung: form.noiDung,
        maDuThaoTruoc: form.maDuThaoTruoc ? Number(form.maDuThaoTruoc) : null,
        ghiChuThayDoi: form.ghiChuThayDoi || null
      });
      navigate(`/chu-de/${form.maChuDe}`);
    } catch (err) {
      setError(err.response?.data || 'Tạo dự thảo thất bại');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Tạo dự thảo</h1>
        <Link to={form.maChuDe ? `/chu-de/${form.maChuDe}` : '/chu-de'} className="btn btn-outline">Quay lại</Link>
      </div>
      {error && <div className="alert alert-error">{String(error)}</div>}
      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="form-row">
            <div className="form-group">
              <label className="form-label">Mã chủ đề</label>
              <input type="number" name="maChuDe" className="form-control" value={form.maChuDe} onChange={onChange} required />
            </div>
            <div className="form-group">
              <label className="form-label">Phiên bản</label>
              <input name="phienBan" className="form-control" value={form.phienBan} onChange={onChange} required />
            </div>
          </div>
          <div className="form-group">
            <label className="form-label">Tên dự thảo</label>
            <input name="tenDuThao" className="form-control" value={form.tenDuThao} onChange={onChange} required />
          </div>
          <div className="form-group">
            <label className="form-label">Nội dung</label>
            <textarea name="noiDung" className="form-control" rows={10} value={form.noiDung} onChange={onChange} required />
          </div>
          <div className="form-row">
            <div className="form-group">
              <label className="form-label">Mã dự thảo trước (nếu có)</label>
              <input type="number" name="maDuThaoTruoc" className="form-control" value={form.maDuThaoTruoc} onChange={onChange} />
            </div>
            <div className="form-group">
              <label className="form-label">Ghi chú thay đổi</label>
              <input name="ghiChuThayDoi" className="form-control" value={form.ghiChuThayDoi} onChange={onChange} />
            </div>
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Đang lưu...' : 'Tạo dự thảo'}
          </button>
        </form>
      </div>
    </div>
  );
}
