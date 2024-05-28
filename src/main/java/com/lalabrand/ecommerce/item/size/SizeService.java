package com.lalabrand.ecommerce.item.size;

import org.springframework.stereotype.Service;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public SizeDTO save(SizeInput sizeInput) {
        return SizeDTO.fromEntity(sizeRepository.save(sizeInput.toEntity()));
    }
}
