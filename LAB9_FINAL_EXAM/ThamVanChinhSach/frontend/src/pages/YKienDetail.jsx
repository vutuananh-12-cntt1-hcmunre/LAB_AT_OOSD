import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

export default function YKienDetail() {
  const { id } = useParams();
  const { isCanBo, isRaSoat } = useAuth();
  const [chiTiet, setChiTiet] = useState(null);
  const [taiLieu, setTaiLieu] = useState([]);
  const [nhomList, setNhomList] = useState([]);
  const [lichSu, setLichSu] = useState([]);
  const [duThaoLienKet, setDuThaoLienKet] = useState([]);
  const [deXuatAI, setDeXuatAI] = useState(null);
  const [dangDeXuat, setDangDeXuat] = useState(false);
  const [error, setError] = useState('');
  const [msg, setMsg] = useState('');
  const [maNhomRaSoat, setMaNhomRaSoat] = useState('');
  const [phanHoi, setPhanHoi] = useState({ ketQuaXuLy: 'Tiếp thu', noiDungGiaiThich: '' });

  const coQuyenRaSoat = isRaSoat || isCanBo;

  const load = () => {
    api.get(`/ykien/${id}`).then((r) => {
      setChiTiet(r.data);
      if (r.data?.maChuDe) {
        api.get(`/nhom-ykien/theo-chude/${r.data.maChuDe}`).then((nr) => setNhomList(nr.data || []));
      }
    }).catch((e) => setError(e.response?.data || 'Không tải được chi tiết'));
    api.get(`/tailieu/theo-ykien/${id}`).then((r) => setTaiLieu(r.data || [])).catch(() => {});
    api.get(`/lich-su/ykien/${id}`).then((r) => setLichSu(r.data || [])).catch(() => {});
    // Tải dự thảo liên kết với ý kiến này (truy vết 2 chiều).
    // Endpoint này cũng được trả trong chi tiết, nhưng gọi riêng để làm mới dữ liệu.
    api.get(`/ykien/${id}/du-thao-lien-ket`).then((r) => setDuThaoLienKet(r.data || [])).catch(() => {});
  };

  useEffect(() => { load(); }, [id]);

  const formatDate = (d) => (d ? new Date(d).toLocaleString('vi-VN') : '—');

  // Gọi AI đề xuất nhóm (hệ thống tự phân nhóm sơ bộ, cán bộ phải rà soát lại)
  const goiDeXuatAI = async () => {
    setDangDeXuat(true);
    setMsg('');
    try {
      const res = await api.post(`/ykien/${id}/de-xuat-nhom`);
      setDeXuatAI(res.data);
      // Nếu có nhóm đề xuất, điền sẵn vào ô rà soát để cán bộ dễ chỉnh
      if (res.data?.maNhomDeXuat) {
        setMaNhomRaSoat(String(res.data.maNhomDeXuat));
      }
    } catch (e) {
      setMsg('Không thể lấy đề xuất tự động: ' + (e.response?.data || e.message));
    } finally {
      setDangDeXuat(false);
    }
  };

  const raSoat = async () => {
    setMsg('');
    try {
      const res = await api.put(`/ykien/${id}/ra-soat`, { maNhomChinhThuc: Number(maNhomRaSoat) });
      setMsg(res.data);
      setDeXuatAI(null);
      load();
    } catch (e) {
      setMsg(e.response?.data || 'Lỗi rà soát');
    }
  };

  const guiPhanHoi = async () => {
    setMsg('');
    try {
      const res = await api.put(`/ykien/${id}/phan-hoi`, phanHoi);
      setMsg(res.data);
      load();
    } catch (e) {
      setMsg(e.response?.data || 'Lỗi phản hồi');
    }
  };

  if (error) return <div className="alert alert-error">{String(error)}</div>;
  if (!chiTiet) return <div className="card">Đang tải...</div>;

  const nhomDeXuatTen = deXuatAI?.maNhomDeXuat
    ? nhomList.find(n => n.maNhom === deXuatAI.maNhomDeXuat)?.tenNhom || `Nhóm #${deXuatAI.maNhomDeXuat}`
    : null;

  return (
    <div>
      <div className="page-header">
        <div>
          <h1>Chi tiết ý kiến #{chiTiet.maYKien}</h1>
          <p className="muted mt-1">Chủ đề: {chiTiet.maChuDe}</p>
        </div>
        <Link to="/y-kien" className="btn btn-outline">Quay lại</Link>
      </div>

      {msg && (
        <div className={`alert ${String(msg).startsWith('Lỗi') ? 'alert-error' : 'alert-success'}`}>
          {String(msg)}
        </div>
      )}

      {/* ── Thông tin ý kiến ── */}
      <div className="card">
        <div className="detail-row">
          <span className="label">Trạng thái</span>
          <span className="badge">{chiTiet.trangThaiXuLy}</span>
        </div>
        <div className="detail-row">
          <span className="label">Thời điểm gửi</span>
          <span>{formatDate(chiTiet.thoiDiemGui)}</span>
        </div>
        <div className="detail-row">
          <span className="label">Nhóm chính thức</span>
          <span>{chiTiet.tenNhomChinhThuc || 'Chưa phân nhóm'}</span>
        </div>
        {chiTiet.thoiDiemRaSoat && (
          <div className="detail-row">
            <span className="label">Thời điểm rà soát</span>
            <span>{formatDate(chiTiet.thoiDiemRaSoat)}</span>
          </div>
        )}
        <div className="detail-row">
          <span className="label">Nội dung</span>
          <span style={{ whiteSpace: 'pre-wrap' }}>{chiTiet.noiDung}</span>
        </div>
        {(chiTiet.ketQuaXuLy || chiTiet.noiDungGiaiThich) && (
          <>
            <div className="detail-row">
              <span className="label">Kết quả xử lý</span>
              <span>{chiTiet.ketQuaXuLy || '—'}</span>
            </div>
            <div className="detail-row">
              <span className="label">Giải thích</span>
              <span style={{ whiteSpace: 'pre-wrap' }}>{chiTiet.noiDungGiaiThich || '—'}</span>
            </div>
            <div className="detail-row">
              <span className="label">Thời điểm phản hồi</span>
              <span>{formatDate(chiTiet.thoiDiemPhanHoi)}</span>
            </div>
          </>
        )}
      </div>

      {/* ── Tài liệu đính kèm ── */}
      {taiLieu.length > 0 && (
        <div className="card">
          <h3 className="card-title">Tài liệu đính kèm</h3>
          <ul style={{ marginTop: '0.75rem', marginLeft: '1.2rem' }}>
            {taiLieu.map((t) => (
              <li key={t.maTaiLieu}>
                {t.tenTep}{' '}
                {t.kichThuoc != null && (
                  <span className="muted">({Math.round(t.kichThuoc / 1024)} KB)</span>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* ── Rà soát phân nhóm (cán bộ / rà soát viên) ── */}
      {coQuyenRaSoat && (
        <div className="card">
          <h3 className="card-title">Rà soát phân nhóm</h3>
          <p className="muted mt-1" style={{ marginBottom: '0.75rem' }}>
            Hệ thống có thể gợi ý nhóm tự động, nhưng{' '}
            <strong>cán bộ rà soát bắt buộc phải xem xét và xác nhận</strong>{' '}
            trước khi nhóm có hiệu lực chính thức. AI không được phép loại bỏ ý kiến.
          </p>

          {/* Khối AI đề xuất */}
          <div style={{
            background: 'var(--view-card, #f8fafc)',
            border: '1px dashed #94a3b8',
            borderRadius: 8,
            padding: '0.85rem 1rem',
            marginBottom: '0.85rem'
          }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', flexWrap: 'wrap' }}>
              <span style={{ fontWeight: 600, fontSize: '0.9rem' }}>Nhóm hệ thống đề xuất (sơ bộ):</span>
              {deXuatAI ? (
                <span style={{ color: '#2563eb', fontWeight: 500 }}>
                  {nhomDeXuatTen}
                  {deXuatAI.doTinCay != null && (
                    <span className="muted" style={{ marginLeft: 6 }}>
                      (độ tin cậy: {Math.round(deXuatAI.doTinCay * 100)}%)
                    </span>
                  )}
                </span>
              ) : (
                <span className="muted">Chưa gọi đề xuất</span>
              )}
              <button
                type="button"
                className="btn btn-outline btn-sm"
                onClick={goiDeXuatAI}
                disabled={dangDeXuat}
              >
                {dangDeXuat ? 'Đang phân tích...' : 'Lấy đề xuất tự động'}
              </button>
            </div>
            <p className="muted" style={{ marginTop: '0.4rem', fontSize: '0.82rem' }}>
              Đây chỉ là gợi ý. Cán bộ rà soát có thể chọn nhóm khác bên dưới.
            </p>
          </div>

          {/* Cán bộ xác nhận nhóm chính thức */}
          <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center', flexWrap: 'wrap' }}>
            <span style={{ fontWeight: 600, fontSize: '0.9rem' }}>Nhóm chính thức (cán bộ xác nhận):</span>
            <select
              className="form-control"
              style={{ maxWidth: 320 }}
              value={maNhomRaSoat}
              onChange={(e) => setMaNhomRaSoat(e.target.value)}
            >
              <option value="">— Chọn nhóm chính thức —</option>
              {nhomList.map((n) => (
                <option key={n.maNhom} value={n.maNhom}>
                  {n.tenNhom}
                </option>
              ))}
            </select>
            <button
              type="button"
              className="btn btn-primary"
              onClick={raSoat}
              disabled={!maNhomRaSoat}
            >
              Xác nhận phân nhóm
            </button>
          </div>
        </div>
      )}

      {/* ── Phản hồi ý kiến (cán bộ cơ quan) ── */}
      {isCanBo && (
        <div className="card">
          <h3 className="card-title">Phản hồi ý kiến</h3>
          <div className="form-group mt-1">
            <label className="form-label">Kết quả xử lý</label>
            <select
              className="form-control"
              value={phanHoi.ketQuaXuLy}
              onChange={(e) => setPhanHoi({ ...phanHoi, ketQuaXuLy: e.target.value })}
            >
              <option value="Tiếp thu">Tiếp thu</option>
              <option value="Tiếp thu một phần">Tiếp thu một phần</option>
              <option value="Không tiếp thu">Không tiếp thu</option>
            </select>
          </div>
          <div className="form-group">
            <label className="form-label">Nội dung giải thích</label>
            <textarea
              className="form-control"
              value={phanHoi.noiDungGiaiThich}
              onChange={(e) => setPhanHoi({ ...phanHoi, noiDungGiaiThich: e.target.value })}
            />
          </div>
          <button type="button" className="btn btn-primary" onClick={guiPhanHoi}>
            Gửi phản hồi
          </button>
        </div>
      )}

      {/* ── Truy vết: Dự thảo tiếp thu ý kiến này ── */}
      <div className="card">
        <h3 className="card-title">Truy vết — Dự thảo tiếp thu ý kiến này</h3>
        {duThaoLienKet.length === 0 ? (
          <p className="muted" style={{ marginTop: '0.5rem' }}>
            Chưa có phiên bản dự thảo nào liên kết với ý kiến này.
          </p>
        ) : (
          <div className="table-wrap mt-1">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Mã DT</th>
                  <th>Tên dự thảo</th>
                  <th>Phiên bản</th>
                  <th>Trạng thái</th>
                  <th>Mô tả thay đổi</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {duThaoLienKet.map((dt) => (
                  <tr key={dt.maDuThao}>
                    <td>{dt.maDuThao}</td>
                    <td>{dt.tenDuThao}</td>
                    <td>{dt.phienBan}</td>
                    <td><span className="badge">{dt.trangThai}</span></td>
                    <td className="muted">{dt.noiDungThayDoi || '—'}</td>
                    <td>
                      <Link to={`/du-thao/${dt.maDuThao}`} className="btn btn-outline btn-sm">Xem</Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* ── Lịch sử xử lý ── */}
      {lichSu.length > 0 && (
        <div className="card">
          <h3 className="card-title">Lịch sử xử lý</h3>
          <div className="table-wrap mt-1">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Thời điểm</th>
                  <th>Hành động</th>
                  <th>Người thực hiện</th>
                  <th>Ghi chú</th>
                </tr>
              </thead>
              <tbody>
                {lichSu.map((ls) => (
                  <tr key={ls.maLichSu}>
                    <td className="muted">{formatDate(ls.thoiDiem)}</td>
                    <td>{ls.hanhDong}</td>
                    <td>{ls.tenNguoiThucHien || ls.maNguoiThucHien || '—'}</td>
                    <td className="muted">{ls.ghiChu || '—'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}
