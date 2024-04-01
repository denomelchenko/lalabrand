package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.utils.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
    @NotBlank(message = "Password can not be blank")
    @Password
    String password;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Invalid email address")
    String email;

    String id;
}

