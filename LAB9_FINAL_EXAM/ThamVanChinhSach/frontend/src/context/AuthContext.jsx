import { createContext, useContext, useState, useEffect } from 'react';
import api from '../api/axios';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const saved = localStorage.getItem('user');
    const token = localStorage.getItem('token');
    if (saved && token) {
      try {
        setUser(JSON.parse(saved));
      } catch {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
      }
    }
    setLoading(false);
  }, []);

  const login = async (email, matKhau) => {
    const res = await api.post('/auth/login', { email, matKhau });
    const data = res.data;
    const userData = {
      maNguoiDung: data.maNguoiDung,
      hoTen: data.hoTen,
      email: data.email,
      maVaiTro: data.maVaiTro,
      tenVaiTro: data.tenVaiTro,
      maCoQuan: data.maCoQuan,
      tenCoQuan: data.tenCoQuan
    };
    localStorage.setItem('token', data.token);
    localStorage.setItem('user', JSON.stringify(userData));
    setUser(userData);
    return userData;
  };

  const register = async (payload) => {
    const res = await api.post('/auth/register', payload);
    return res.data;
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  const isAdmin = user?.maVaiTro === 'ADMIN';
  const isCanBo = user?.maVaiTro === 'CAN_BO_CO_QUAN' || isAdmin;
  const isRaSoat = user?.maVaiTro === 'RA_SOAT' || isAdmin;

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout, isAdmin, isCanBo, isRaSoat }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
