import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

/** Chuẩn hóa mọi lỗi đăng nhập thành câu tiếng Việt ngắn, không lộ SQL. */
function thongBaoDangNhap(raw) {
  const msg = String(raw || '').trim();
  const lower = msg.toLowerCase();

  if (msg.includes('Email không tồn tại') || lower.includes('email không tồn tại')) {
    return 'Email không tồn tại trong hệ thống!';
  }
  if (msg.includes('Mật khẩu không đúng') || lower.includes('mật khẩu không đúng')) {
    return 'Mật khẩu không đúng!';
  }
  if (msg.includes('bị khóa') || lower.includes('đã bị khóa')) {
    return 'Tài khoản đã bị khóa. Vui lòng liên hệ quản trị viên.';
  }
  if (
    lower.includes('preparedstatement') ||
    lower.includes('sqlexception') ||
    lower.includes('jdbc') ||
    lower.includes('sql [') ||
    lower.includes('error code')
  ) {
    return 'Email hoặc mật khẩu không đúng!';
  }
  // Body response đôi khi là object
  if (msg === '[object Object]') {
    return 'Email hoặc mật khẩu không đúng!';
  }
  if (msg.length > 120) {
    return 'Email hoặc mật khẩu không đúng!';
  }
  return msg || 'Đăng nhập thất bại. Vui lòng thử lại.';
}

export default function Login() {
  const { login, user } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [matKhau, setMatKhau] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (user) {
      navigate('/');
    }
  }, [user, navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await login(email.trim(), matKhau);
      navigate('/');
    } catch (err) {
      const raw = err.response?.data ?? err.message ?? '';
      setError(thongBaoDangNhap(raw));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-box">
        <h1>Đăng nhập</h1>
        <p className="auth-subtitle">Hệ thống Tham vấn Chính sách</p>
        {error && <div className="alert alert-error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Email</label>
            <input
              type="email"
              className="form-control"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              autoFocus
              placeholder="vd:...@email.com"
            />
          </div>
          <div className="form-group">
            <label className="form-label">Mật khẩu</label>
            <input
              type="password"
              className="form-control"
              value={matKhau}
              onChange={(e) => setMatKhau(e.target.value)}
              required
              placeholder="..."
            />
          </div>
          <button type="submit" className="btn btn-primary btn-block" disabled={loading}>
            {loading ? 'Đang đăng nhập...' : 'Đăng nhập'}
          </button>
        </form>
        <p className="auth-footer">
          Chưa có tài khoản? <Link to="/register">Đăng ký</Link>
        </p>
      </div>
    </div>
  );
}