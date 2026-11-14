package com.example.ThamVanChinhSach.service;

import org.springframework.stereotype.Service;

import com.example.ThamVanChinhSach.dto.ThongKeYKienResponse;
import com.example.ThamVanChinhSach.repository.ThongKeRepository;

@Service
public class ThongKeService {
    private final ThongKeRepository thongKeRepository;

    public ThongKeService(ThongKeRepository thongKeRepository) {
        this.thongKeRepository = thongKeRepository;
    }

    public ThongKeYKienResponse layThongKeChung() {
        return thongKeRepository.layThongKeChung();
    }
}