package com.lalabrand.ecommerce.item.size;

import com.lalabrand.ecommerce.item.enums.SizeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

/**
 * DTO for {@link Size}
 */
@Value
@Getter
@Setter
@Builder
public class SizeDTO {
    String id;
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
