package com.lalabrand.ecommerce.item.size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;
    private final Logger logger = LoggerFactory.getLogger(SizeService.class);

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
                .orElseThrow(() -> {
                        logger.error("Size with id + {} does not exist", sizeId);
                    return new IllegalArgumentException("Size with id: " + sizeId + " does not exist");
                });
    }
}
