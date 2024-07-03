package com.lalabrand.ecommerce.order.shipping.shipping_info;

import com.lalabrand.ecommerce.order.shipping.shipping_option.ShippingOptionDTO;
import com.lalabrand.ecommerce.user.enums.Country;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder
public class ShippingInfoDTO implements Serializable {
    String id;
    Country country;
    String zip;
    String city;
    String address1;
    String address2;
    String phone;
    ShippingOptionDTO shippingOptionDTO;

    public static ShippingInfoDTO fromEntity(ShippingInfo shippingInfo) {
        return ShippingInfoDTO.builder()
                .id(shippingInfo.getId())
                .country(shippingInfo.getCountry())
                .zip(shippingInfo.getZip())
                .city(shippingInfo.getCity())
                .address1(shippingInfo.getAddress1())
                .address2(shippingInfo.getAddress2())
                .phone(shippingInfo.getPhone())
                .shippingOptionDTO(ShippingOptionDTO.fromEntity(shippingInfo.getShippingOption()))
                .build();
    }

    public ShippingInfo toEntity() {
        return ShippingInfo.builder()
                .id(this.id)
                .country(this.country)
                .zip(this.zip)
                .city(this.city)
                .address1(this.address1)
                .address2(this.address2)
                .phone(this.phone)
                .shippingOption(this.shippingOptionDTO.toEntity())
                .build();
    }
}
