package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.user.enums.Language;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserUpdateRequest implements Serializable {
    String firstName;
    String lastName;
    String email;
    String phone;
    Language language;
}
