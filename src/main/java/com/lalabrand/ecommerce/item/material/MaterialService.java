package com.lalabrand.ecommerce.item.material;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public MaterialDTO create(String name) {
        return MaterialDTO.fromEntity(materialRepository.save(Material.builder()
                .name(name)
                .build()
        ));
    }

    public Set<MaterialDTO> findAll() {
        return materialRepository.findAll().stream().map(MaterialDTO::fromEntity).collect(Collectors.toSet());
    }

    public Optional<Material> findByName(String name) {
        return materialRepository.findByName(name);
    }
}
