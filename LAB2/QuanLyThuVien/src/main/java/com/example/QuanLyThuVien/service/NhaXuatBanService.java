package com.example.quanlythuvien.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.quanlythuvien.entity.NhaXuatBan;
import com.example.quanlythuvien.repository.NhaXuatBanRepository;

@Service
public class NhaXuatBanService {

    private final NhaXuatBanRepository nhaXuatBanRepository;

    public NhaXuatBanService(NhaXuatBanRepository nhaXuatBanRepository) {
        this.nhaXuatBanRepository = nhaXuatBanRepository;
    }

    public List<NhaXuatBan> findAll() {
        return nhaXuatBanRepository.findAll();
    }

    public Optional<NhaXuatBan> findById(String maNhaXuatBan) {
        return nhaXuatBanRepository.findById(maNhaXuatBan);
    }

    public NhaXuatBan save(NhaXuatBan nhaXuatBan) {
        return nhaXuatBanRepository.save(nhaXuatBan);
    }

    public void deleteById(String maNhaXuatBan) {
        nhaXuatBanRepository.deleteById(maNhaXuatBan);
    }

    public boolean existsById(String maNhaXuatBan) {
        return nhaXuatBanRepository.existsById(maNhaXuatBan);
    }
}