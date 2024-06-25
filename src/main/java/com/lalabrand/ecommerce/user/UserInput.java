package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.utils.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInput {
    @NotBlank(message = "Password can not be blank")
    @Password
    String password;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Invalid email address")
    String email;
}

