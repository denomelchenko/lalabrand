package com.lalabrand.ecommerce.security.jwt_token;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtPayload {
    private String email;
    private Date expirationDate;
    private String id;

    public JwtPayload(String id, String email, Date expirationDate) {
        this.id = id;
        this.email = email;
        this.expirationDate = expirationDate;
    }
}
