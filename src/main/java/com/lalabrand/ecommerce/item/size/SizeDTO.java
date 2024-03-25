package com.lalabrand.ecommerce.item.size;

import com.lalabrand.ecommerce.item.enums.SizeType;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link Size}
 */
@Value
@Builder
public class SizeDto {
    Integer id;
    SizeType sizeType;
    String value;

    public static SizeDto fromEntity(Size size) {
        return SizeDto.builder()
                .id(size.getId())
                .sizeType(size.getSizeType())
                .value(size.getValue())
                .build();
    }
}
