package com.lalabrand.ecommerce.item.material;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Material}
 */
@Value
@Builder
public class MaterialDTO implements Serializable {
    @NotNull
    @Size(max = 30)
    String name;

    public static MaterialDTO fromEntity(Material material) {
        return MaterialDTO.builder()
                .name(material.getName())
                .build();
    }
}