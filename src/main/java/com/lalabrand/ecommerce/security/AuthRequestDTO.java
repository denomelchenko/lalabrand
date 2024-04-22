package com.lalabrand.ecommerce.security;

import com.lalabrand.ecommerce.utils.Password;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {
    @Email
    private String email;

    @Password
    private String password;
}
