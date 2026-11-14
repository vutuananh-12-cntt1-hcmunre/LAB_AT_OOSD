package com.example.ThamVanChinhSach.dto;

public class LienKetThayDoiRequest {
    private int maDuThaoMoi;
    private int maYKien;
    private Integer maPhanHoi;
    private String noiDungThayDoi;

    public int getMaDuThaoMoi() {
        return maDuThaoMoi;
    }

    public void setMaDuThaoMoi(int maDuThaoMoi) {
        this.maDuThaoMoi = maDuThaoMoi;
    }

    public int getMaYKien() {
        return maYKien;
    }

    public void setMaYKien(int maYKien) {
        this.maYKien = maYKien;
    }

    public Integer getMaPhanHoi() {
        return maPhanHoi;
    }

    public void setMaPhanHoi(Integer maPhanHoi) {
        this.maPhanHoi = maPhanHoi;
    }

    public String getNoiDungThayDoi() {
        return noiDungThayDoi;
    }

    public void setNoiDungThayDoi(String noiDungThayDoi) {
        this.noiDungThayDoi = noiDungThayDoi;
    }
}
