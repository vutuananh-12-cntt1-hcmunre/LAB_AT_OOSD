import { useEffect, useState } from 'react';
import api from '../api/axios';

export default function QuanTri() {
  const [tab, setTab] = useState('coquan');
  const [coQuan, setCoQuan] = useState([]);
  const [nguoiDung, setNguoiDung] = useState([]);
  const [error, setError] = useState('');
  const [msg, setMsg] = useState('');
  const [cqForm, setCqForm] = useState({ tenCoQuan: '', emailLienHe: '', soDienThoai: '', diaChi: '' });
  const [editUser, setEditUser] = useState(null);

  const loadCQ = () => api.get('/coquan/danh-sach').then((r) => setCoQuan(r.data || [])).catch((e) => setError(String(e.response?.data || e.message)));
  const loadND = () => api.get('/nguoidung/danh-sach').then((r) => setNguoiDung(r.data || [])).catch((e) => setError(String(e.response?.data || e.message)));

  useEffect(() => {
    loadCQ();
    loadND();
  }, []);

  const taoCoQuan = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      const res = await api.post('/coquan/tao-moi', cqForm);
      setMsg(res.data);
      setCqForm({ tenCoQuan: '', emailLienHe: '', soDienThoai: '', diaChi: '' });
      loadCQ();
    } catch (err) {
      setMsg(err.response?.data || 'Lỗi tạo cơ quan');
    }
  };

  const luuUser = async () => {
    if (!editUser) return;
    setMsg('');
    try {
      const res = await api.put(`/nguoidung/${editUser.maNguoiDung}`, {
        maVaiTro: editUser.maVaiTro,
        maCoQuan: editUser.maCoQuan ? Number(editUser.maCoQuan) : null,
        trangThai: editUser.trangThai
      });
      setMsg(res.data);
      setEditUser(null);
      loadND();
    } catch (err) {
      setMsg(err.response?.data || 'Lỗi cập nhật người dùng');
    }
  };

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>Quản trị hệ thống</h1>
          <p className="muted mt-1">Quản lý cơ quan và người dùng</p>
        </div>
      </div>

      {error && <div className="alert alert-error">{error}</div>}
      {msg && <div className={`alert ${String(msg).startsWith('Lỗi') ? 'alert-error' : 'alert-success'}`}>{String(msg)}</div>}

      <div className="flex gap-1 mb-2">
        <button type="button" className={`btn ${tab === 'coquan' ? 'btn-primary' : 'btn-outline'}`} onClick={() => setTab('coquan')}>Cơ quan</button>
        <button type="button" className={`btn ${tab === 'nguoidung' ? 'btn-primary' : 'btn-outline'}`} onClick={() => setTab('nguoidung')}>Người dùng</button>
      </div>

      {tab === 'coquan' && (
        <>
          <div className="card">
            <h3 className="card-title">Thêm cơ quan</h3>
            <form onSubmit={taoCoQuan} className="mt-1">
              <div className="form-row">
                <div className="form-group">
                  <label className="form-label">Tên cơ quan</label>
                  <input className="form-control" value={cqForm.tenCoQuan} onChange={(e) => setCqForm({ ...cqForm, tenCoQuan: e.target.value })} required />
                </div>
                <div className="form-group">
                  <label className="form-label">Email liên hệ</label>
                  <input className="form-control" value={cqForm.emailLienHe} onChange={(e) => setCqForm({ ...cqForm, emailLienHe: e.target.value })} />
                </div>
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label className="form-label">Số điện thoại</label>
                  <input className="form-control" value={cqForm.soDienThoai} onChange={(e) => setCqForm({ ...cqForm, soDienThoai: e.target.value })} />
                </div>
                <div className="form-group">
                  <label className="form-label">Địa chỉ</label>
                  <input className="form-control" value={cqForm.diaChi} onChange={(e) => setCqForm({ ...cqForm, diaChi: e.target.value })} />
                </div>
              </div>
              <button type="submit" className="btn btn-primary">Thêm cơ quan</button>
            </form>
          </div>
          <div className="card" style={{ padding: 0 }}>
            <div className="table-wrap">
              <table className="data-table">
                <thead>
                  <tr><th>Mã</th><th>Tên</th><th>Email</th><th>SĐT</th><th>Trạng thái</th></tr>
                </thead>
                <tbody>
                  {coQuan.map((c) => (
                    <tr key={c.maCoQuan}>
                      <td>{c.maCoQuan}</td>
                      <td>{c.tenCoQuan}</td>
                      <td className="muted">{c.emailLienHe || '—'}</td>
                      <td className="muted">{c.soDienThoai || '—'}</td>
                      <td>{c.trangThai ? 'Hoạt động' : 'Ngừng'}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </>
      )}

      {tab === 'nguoidung' && (
        <div className="card" style={{ padding: 0 }}>
          <div className="table-wrap">
            <table className="data-table">
              <thead>
                <tr><th>Mã</th><th>Họ tên</th><th>Email</th><th>Vai trò</th><th>Cơ quan</th><th>TT</th><th></th></tr>
              </thead>
              <tbody>
                {nguoiDung.map((u) => (
                  <tr key={u.maNguoiDung}>
                    <td>{u.maNguoiDung}</td>
                    <td>{u.hoTen}</td>
                    <td className="muted">{u.email}</td>
                    <td>{u.tenVaiTro || u.maVaiTro}</td>
                    <td className="muted">{u.tenCoQuan || '—'}</td>
                    <td>{u.trangThai ? 'ON' : 'OFF'}</td>
                    <td>
                      <button type="button" className="btn btn-outline btn-sm" onClick={() => setEditUser({ ...u })}>Sửa</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {editUser && (
        <div className="card">
          <h3 className="card-title">Cập nhật: {editUser.hoTen}</h3>
          <div className="form-row mt-1">
            <div className="form-group">
              <label className="form-label">Vai trò</label>
              <select className="form-control" value={editUser.maVaiTro} onChange={(e) => setEditUser({ ...editUser, maVaiTro: e.target.value })}>
                <option value="NGUOI_DAN">Người dân</option>
                <option value="RA_SOAT">Cán bộ rà soát</option>
                <option value="CAN_BO_CO_QUAN">Cán bộ cơ quan</option>
                <option value="ADMIN">Quản trị viên</option>
              </select>
            </div>
            <div className="form-group">
              <label className="form-label">Mã cơ quan</label>
              <input type="number" className="form-control" value={editUser.maCoQuan ?? ''} onChange={(e) => setEditUser({ ...editUser, maCoQuan: e.target.value })} />
            </div>
            <div className="form-group">
              <label className="form-label">Trạng thái</label>
              <select className="form-control" value={editUser.trangThai ? '1' : '0'} onChange={(e) => setEditUser({ ...editUser, trangThai: e.target.value === '1' })}>
                <option value="1">Hoạt động</option>
                <option value="0">Khóa</option>
              </select>
            </div>
          </div>
          <div className="actions">
            <button type="button" className="btn btn-primary" onClick={luuUser}>Lưu</button>
            <button type="button" className="btn btn-outline" onClick={() => setEditUser(null)}>Huỷ</button>
          </div>
        </div>
      )}
    </div>
  );
}
