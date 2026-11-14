import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Layout() {
  const { user, logout, isCanBo, isAdmin, isRaSoat } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="app-shell">
      <header className="navbar">
        <div className="navbar-inner">
          <NavLink to="/" className="navbar-brand">
            Hệ thống Tham vấn Chính sách
          </NavLink>
          <nav className="navbar-nav">
            <NavLink to="/" className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`} end>
              Trang chủ
            </NavLink>
            <NavLink to="/chu-de" className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
              Chủ đề
            </NavLink>
            {user && (
              <NavLink to="/y-kien" className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
                Ý kiến
              </NavLink>
            )}
            {user && (
              <NavLink to="/y-kien/cua-toi" className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
                Ý kiến của tôi
              </NavLink>
            )}
            {isCanBo && (
              <NavLink to="/thong-ke" className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
                Thống kê
              </NavLink>
            )}
            {isAdmin && (
              <NavLink to="/quan-tri" className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
                Quản trị
              </NavLink>
            )}
            <NavLink to="/cong-bo" className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
              Công bố
            </NavLink>
            {user ? (
              <>
                <span className="nav-user">
                  {user.hoTen} ({user.tenVaiTro || user.maVaiTro})
                </span>
                <button type="button" className="btn btn-sm btn-outline" style={{ color: '#eee', borderColor: '#8a9bb5' }} onClick={handleLogout}>
                  Đăng xuất
                </button>
              </>
            ) : (
              <NavLink to="/login" className="nav-link">Đăng nhập</NavLink>
            )}
          </nav>
        </div>
      </header>

      <main className="main-content">
        <Outlet />
      </main>

      <footer className="footer-bar">
        Hệ thống Tham vấn Chính sách — Góp ý xây dựng chính sách công
      </footer>
    </div>
  );
}
