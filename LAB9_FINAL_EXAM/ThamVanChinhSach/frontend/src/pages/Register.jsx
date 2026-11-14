import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Register() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    hoTen: '',
    email: '',
    matKhau: '',
    soDienThoai: '',
    diaChi: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);
    try {
      const msg = await register({ ...form, maVaiTro: 'NGUOI_DAN' });
      setSuccess(typeof msg === 'string' ? msg : 'Đăng ký thành công');
      setTimeout(() => navigate('/login'), 1500);
    } catch (err) {
      const msg = err.response?.data || err.message || 'Đăng ký thất bại';
      setError(typeof msg === 'string' ? msg : 'Đăng ký thất bại');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-box" style={{ maxWidth: 480 }}>
        <h1>Đăng ký tài khoản</h1>
        <p className="auth-subtitle">Người dân tham gia góp ý chính sách</p>
        {error && <div className="alert alert-error">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Họ và tên</label>
            <input name="hoTen" className="form-control" value={form.hoTen} onChange={onChange} required />
          </div>
          <div className="form-group">
            <label className="form-label">Email</label>
            <input type="email" name="email" className="form-control" value={form.email} onChange={onChange} required />
          </div>
          <div className="form-group">
            <label className="form-label">Mật khẩu</label>
            <input type="password" name="matKhau" className="form-control" value={form.matKhau} onChange={onChange} required />
          </div>
          <div className="form-group">
            <label className="form-label">Số điện thoại</label>
            <input name="soDienThoai" className="form-control" value={form.soDienThoai} onChange={onChange} />
          </div>
          <div className="form-group">
            <label className="form-label">Địa chỉ</label>
            <input name="diaChi" className="form-control" value={form.diaChi} onChange={onChange} />
          </div>
          <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
            {loading ? 'Đang xử lý...' : 'Đăng ký'}
          </button>
        </form>
        <p className="auth-footer">
          Đã có tài khoản? <Link to="/login">Đăng nhập</Link>
        </p>
      </div>
    </div>
  );
}
