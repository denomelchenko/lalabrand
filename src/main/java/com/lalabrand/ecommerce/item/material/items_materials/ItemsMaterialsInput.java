package com.lalabrand.ecommerce.item.material.items_materials;

import com.lalabrand.ecommerce.utils.annotation.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ItemsMaterialsInput {
    @Id
    String itemId;

    @NotNull
    @NotBlank
    String materialName;

    @Min(0)
    @Max(100)
    Integer percentage;
}
