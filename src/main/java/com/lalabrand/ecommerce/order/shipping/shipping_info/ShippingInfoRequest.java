package com.lalabrand.ecommerce.order.shipping.shipping_info;

import com.lalabrand.ecommerce.user.enums.Country;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShippingInfoRequest {
    Country country;
    @NotBlank
    String city;
    @NotBlank
    String address1;
    @NotBlank
    String address2;
    @NotBlank
    String phone;
    @NotBlank
    String zip;
    @NotBlank
    String shippingOptionId;

    public ShippingInfo toEntity() {
        return ShippingInfo.builder()
                .country(this.country)
                .zip(this.zip)
                .city(this.city)
                .address1(this.address1)
                .address2(this.address2)
                .phone(this.phone)
                .shippingOptionId(this.shippingOptionId)
                .build();
    }
}
