package com.lalabrand.ecommerce.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    private static final Integer MILLIS_IN_SECOND = 1000;
    @Value("${secret}")
    private String key;

    private byte[] decodedKey;

    @Value("${access.token.expiration.seconds}")
    private Long accessTokenExpirationSec;

    public boolean validateToken(JwtPayload jwtPayload, UserDetailsImpl userDetails) {
        return (!isNotExpired(jwtPayload) && jwtPayload.getId().equals(userDetails.getId()) && jwtPayload.getEmail().equals(userDetails.getUsername()));
    }

    private boolean isNotExpired(JwtPayload token) {
        return token.getExpirationDate().before(new Date());
    }

    private SecretKey getKey() {
        if (decodedKey == null) {
            decodedKey = Decoders.BASE64.decode(key);
        }
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(String email, Integer id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String email) {
        Date currentDate = new Date();
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(currentDate)
                .expiration(new Date(currentDate.getTime() + accessTokenExpirationSec * MILLIS_IN_SECOND))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    public JwtPayload parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Integer id = (Integer) claims.get("id");
        String subject = claims.getSubject();
        Date expirationDate = claims.getExpiration();

        return new JwtPayload(id, subject, expirationDate);
    }
}
