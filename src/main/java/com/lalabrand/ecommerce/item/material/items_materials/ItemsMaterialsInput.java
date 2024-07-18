package com.lalabrand.ecommerce.item.material.items_materials;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.UUID;

@Value
public class ItemsMaterialsInput {
    @UUID(message = "ItemId is not valid")
    String itemId;

    @NotNull
    @NotBlank
    String materialName;

    @Min(0)
    @Max(100)
    Integer percentage;
}
