import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import ChuDeList from './pages/ChuDeList';
import ChuDeDetail from './pages/ChuDeDetail';
import ChuDeCreate from './pages/ChuDeCreate';
import YKienList from './pages/YKienList';
import YKienGui from './pages/YKienGui';
import YKienDetail from './pages/YKienDetail';
import ThongKe from './pages/ThongKe';
import CongBo from './pages/CongBo';
import QuanTri from './pages/QuanTri';
import DuThaoCreate from './pages/DuThaoCreate';
import DuThaoDetail from './pages/DuThaoDetail';

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route element={<Layout />}>
            <Route path="/" element={
              <ProtectedRoute>
                <Home />
              </ProtectedRoute>
            } />
            <Route path="/chu-de" element={<ChuDeList />} />
            <Route path="/chu-de/tao" element={
              <ProtectedRoute roles={['CAN_BO_CO_QUAN', 'ADMIN']}>
                <ChuDeCreate />
              </ProtectedRoute>
            } />
            <Route path="/chu-de/:id" element={<ChuDeDetail />} />
            <Route path="/y-kien/gui" element={
              <ProtectedRoute>
                <YKienGui />
              </ProtectedRoute>
            } />
            <Route path="/y-kien/cua-toi" element={
              <ProtectedRoute>
                <YKienList caNhan />
              </ProtectedRoute>
            } />
            <Route path="/y-kien" element={
              <ProtectedRoute>
                <YKienList />
              </ProtectedRoute>
            } />
            <Route path="/y-kien/:id" element={
              <ProtectedRoute>
                <YKienDetail />
              </ProtectedRoute>
            } />
            <Route path="/thong-ke" element={
              <ProtectedRoute roles={['CAN_BO_CO_QUAN', 'ADMIN']}>
                <ThongKe />
              </ProtectedRoute>
            } />
            <Route path="/cong-bo" element={<CongBo />} />
            <Route path="/quan-tri" element={
              <ProtectedRoute roles={['ADMIN']}>
                <QuanTri />
              </ProtectedRoute>
            } />
            <Route path="/du-thao/tao" element={
              <ProtectedRoute roles={['CAN_BO_CO_QUAN', 'ADMIN']}>
                <DuThaoCreate />
              </ProtectedRoute>
            } />
            <Route path="/du-thao/:id" element={<DuThaoDetail />} />
            {/* :id routes must stay after static paths */}
          </Route>

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
