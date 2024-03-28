package com.lalabrand.ecommerce.item.size;

import com.lalabrand.ecommerce.item.enums.SizeType;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link Size}
 */
@Value
@Builder
public class SizeDTO {
    Integer id;
    SizeType sizeType;
    String value;

    public static SizeDTO fromEntity(Size size) {
        return SizeDTO.builder()
                .id(size.getId())
                .sizeType(size.getSizeType())
                .value(size.getValue())
                .build();
    }
}
