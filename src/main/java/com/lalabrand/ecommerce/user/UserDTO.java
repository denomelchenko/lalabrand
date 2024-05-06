package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.user.enums.Language;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

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

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .bonus(user.getBonus())
                .language(user.getLanguage())
                .build();
    }
}
