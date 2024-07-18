package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.user.address.Address;
import com.lalabrand.ecommerce.user.address.AddressDTO;
import com.lalabrand.ecommerce.user.enums.Language;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link User}
 */
@Value
@Builder
public class UserDTO implements Serializable {
    String id;
    String firstName;
    String lastName;
    String email;
    String phone;
    Integer bonus;
    Language language;
    Set<AddressDTO> addresses;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .bonus(user.getBonus())
                .language(user.getLanguage())
                .addresses(user.getAddresses().stream().map(AddressDTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
