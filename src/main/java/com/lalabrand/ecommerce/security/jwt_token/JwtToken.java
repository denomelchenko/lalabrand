package com.lalabrand.ecommerce.auth.jwt_token;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JwtToken {

    private String subject;
    private Date expirationDate;
    private String token;

    public JwtToken(String subject, Date expirationDate, String token) {
        this.subject = subject;
        this.expirationDate = expirationDate;
        this.token = token;
    }
}
