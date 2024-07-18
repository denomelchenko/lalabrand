package com.lalabrand.ecommerce.item.category;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDTO {
    String name;

    public static CategoryDTO fromEntity(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .build();
    }
}
