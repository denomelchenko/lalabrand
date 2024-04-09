package com.lalabrand.ecommerce.security.password_reset;

import com.lalabrand.ecommerce.utils.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PasswordResetRequest {
    @Email
    private String email;
    @NotBlank
    private String token;
    @Password
    private String password;
}
