package com.lalabrand.ecommerce.item.size;


import com.lalabrand.ecommerce.item.enums.SizeType;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Getter
@Setter
public class SizeInput {
    SizeType sizeType;
    String value;

    public Size toEntity() {
        return Size.builder()
                .sizeType(this.sizeType)
                .value(this.value)
                .build();
    }
}
