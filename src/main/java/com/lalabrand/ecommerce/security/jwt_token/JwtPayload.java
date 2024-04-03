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
    private Integer passwordVersion;

    public JwtPayload(String id, String email, Integer passwordVersion, Date expirationDate) {
        this.id = id;
        this.email = email;
        this.passwordVersion = passwordVersion;
        this.expirationDate = expirationDate;
    }
}
