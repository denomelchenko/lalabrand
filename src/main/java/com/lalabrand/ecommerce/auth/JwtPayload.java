package com.lalabrand.ecommerce.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtPayload {
    private String email;
    private Date expirationDate;

    public JwtPayload(String email, Date expirationDate) {
        this.email = email;
        this.expirationDate = expirationDate;
    }
}
