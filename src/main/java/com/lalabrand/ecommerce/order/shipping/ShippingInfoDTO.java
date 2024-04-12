package com.lalabrand.ecommerce.order.shipping;

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

    public static ShippingInfoDTO fromEntity(ShippingInfo shippingInfo){
        return ShippingInfoDTO.builder()
                .id(shippingInfo.getId())
                .country(shippingInfo.getCountry())
                .zip(shippingInfo.getZip())
                .city(shippingInfo.getCity())
                .address1(shippingInfo.getAddress1())
                .address2(shippingInfo.getAddress2())
                .phone(shippingInfo.getPhone())
                .build();
    }

    public ShippingInfo toEntity() {
        ShippingInfo shippingInfo = new ShippingInfo();
        shippingInfo.setId(this.id);
        shippingInfo.setCountry(this.country);
        shippingInfo.setZip(this.zip);
        shippingInfo.setCity(this.city);
        shippingInfo.setAddress1(this.address1);
        shippingInfo.setAddress2(this.address2);
        shippingInfo.setPhone(this.phone);
        return shippingInfo;
    }
}
