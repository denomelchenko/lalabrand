package com.lalabrand.ecommerce.item.material.items_materials;

import com.lalabrand.ecommerce.utils.annotation.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ItemsMaterials}
 */
@Value
@Builder
public class ItemsMaterialsDTO implements Serializable {
    @Id
    String itemId;

    @NotNull
    @NotBlank
    String materialName;

    @Min(0)
    @Max(100)
    Integer percentage;

    public static ItemsMaterialsDTO fromEntity(ItemsMaterials itemsMaterials) {
        return ItemsMaterialsDTO.builder()
                .itemId(itemsMaterials.getItemId())
                .materialName(itemsMaterials.getMaterialName())
                .percentage(itemsMaterials.getPercentage())
                .build();
    }
}