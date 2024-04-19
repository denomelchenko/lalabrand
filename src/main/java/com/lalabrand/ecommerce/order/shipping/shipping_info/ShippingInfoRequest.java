package com.lalabrand.ecommerce.order.shipping.shipping_info;

import com.lalabrand.ecommerce.user.enums.Country;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShippingInfoRequest {
    Country country;
    String city;
    String address1;
    String address2;
    String phone;
    String zip;
    String shippingOptionId;
}
