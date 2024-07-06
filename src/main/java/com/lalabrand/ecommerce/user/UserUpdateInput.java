package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.utils.annotation.PhoneNumber;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserUpdateInput implements Serializable {
    @Size(min = 2, max = 50)
    String firstName;
    @Size(min = 2, max = 50)
    String lastName;
    @PhoneNumber
    String phone;
    Language language;
}
