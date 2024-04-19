package com.lalabrand.ecommerce.order.shipping.shipping_option;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ShippingOptionDTO {
    String id;
    String name;
    BigDecimal price;

    public static ShippingOptionDTO fromEntity(ShippingOption shippingOption){
        return ShippingOptionDTO.builder()
                .id(shippingOption.getId())
                .name(shippingOption.getName())
                .price(shippingOption.getPrice())
                .build();
    }

    public ShippingOption toEntity() {
        return ShippingOption.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .build();
    }
}
