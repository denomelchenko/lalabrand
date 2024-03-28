package com.lalabrand.ecommerce.item.category;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDTO {
    String id;
    String name;

    public static CategoryDTO fromEntity(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
