import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../api/axios';

export default function ChuDeCreate() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    maCoQuan: 1,
    tieuDe: '',
    noiDungMoTa: '',
    thoiGianBatDau: '',
    thoiGianKetThuc: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await api.post('/chude/tao-moi', {
        ...form,
        maCoQuan: Number(form.maCoQuan),
        thoiGianBatDau: form.thoiGianBatDau ? new Date(form.thoiGianBatDau).toISOString() : null,
        thoiGianKetThuc: form.thoiGianKetThuc ? new Date(form.thoiGianKetThuc).toISOString() : null
      });
      navigate('/chu-de');
    } catch (err) {
      setError(err.response?.data || 'Tạo chủ đề thất bại');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1>Tạo chủ đề tham vấn</h1>
        <Link to="/chu-de" className="btn btn-outline">Quay lại</Link>
      </div>
      {error && <div className="alert alert-error">{String(error)}</div>}
      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Mã cơ quan</label>
            <input type="number" name="maCoQuan" className="form-control" value={form.maCoQuan} onChange={onChange} required />
          </div>
          <div className="form-group">
            <label className="form-label">Tiêu đề</label>
            <input name="tieuDe" className="form-control" value={form.tieuDe} onChange={onChange} required />
          </div>
          <div className="form-group">
            <label className="form-label">Nội dung mô tả</label>
            <textarea name="noiDungMoTa" className="form-control" value={form.noiDungMoTa} onChange={onChange} />
          </div>
          <div className="form-row">
            <div className="form-group">
              <label className="form-label">Thời gian bắt đầu</label>
              <input type="datetime-local" name="thoiGianBatDau" className="form-control" value={form.thoiGianBatDau} onChange={onChange} required />
            </div>
            <div className="form-group">
              <label className="form-label">Thời gian kết thúc</label>
              <input type="datetime-local" name="thoiGianKetThuc" className="form-control" value={form.thoiGianKetThuc} onChange={onChange} required />
            </div>
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Đang lưu...' : 'Tạo chủ đề'}
          </button>
        </form>
      </div>
    </div>
  );
}
