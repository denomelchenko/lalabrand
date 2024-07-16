package com.lalabrand.ecommerce.user.address;

import com.lalabrand.ecommerce.user.enums.Country;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */
@Value
@Builder
public class AddressDTO implements Serializable {
    String id;
    Country country;
    String zip;
    String city;
    String address1;
    String address2;

    public static AddressDTO fromEntity(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .country(address.getCountry())
                .zip(address.getZip())
                .city(address.getCity())
                .address1(address.getAddress1())
                .address2(address.getAddress2())
                .build();
    }
}