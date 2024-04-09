package com.lalabrand.ecommerce.security.jwt_token;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtPayload {
    private String email;
    private Date expirationDate;
    private String userId;
    private Integer passwordVersion;

    public JwtPayload(String userId, String email, Integer passwordVersion, Date expirationDate) {
        this.userId = userId;
        this.email = email;
        this.passwordVersion = passwordVersion;
        this.expirationDate = expirationDate;
    }
}
