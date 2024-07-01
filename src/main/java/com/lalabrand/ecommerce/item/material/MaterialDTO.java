package com.lalabrand.ecommerce.item.material;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Material}
 */
@Value
public class MaterialDTO implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 30)
    String name;
}