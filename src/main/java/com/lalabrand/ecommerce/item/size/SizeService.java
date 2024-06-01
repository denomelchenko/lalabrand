package com.lalabrand.ecommerce.item.size;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public SizeDTO save(SizeInput sizeInput) {
        return SizeDTO.fromEntity(sizeRepository.save(sizeInput.toEntity()));
    }

    public Set<SizeDTO> findAll() {
        return sizeRepository.findAll()
                .stream()
                .map(SizeDTO::fromEntity)
                .collect(Collectors.toSet());
    }

    public Size findSizeById(String sizeId) {
        return sizeRepository.findById(sizeId)
                .orElseThrow(() -> new IllegalArgumentException("Size with this id does not exist"));
    }
}
