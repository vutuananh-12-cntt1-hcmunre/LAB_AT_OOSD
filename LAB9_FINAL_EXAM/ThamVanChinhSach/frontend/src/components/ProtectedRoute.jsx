import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function ProtectedRoute({ children, roles }) {
  const { user, loading } = useAuth();

  if (loading) {
    return <div className="card">Đang tải...</div>;
  }

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (roles && roles.length > 0 && !roles.includes(user.maVaiTro)) {
    return (
      <div className="card">
        <div className="alert alert-error">Bạn không có quyền truy cập trang này.</div>
      </div>
    );
  }

  return children;
}
