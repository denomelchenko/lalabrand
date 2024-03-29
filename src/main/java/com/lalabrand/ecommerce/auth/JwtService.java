package com.lalabrand.ecommerce.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    @Value("${secret}")
    private String key;

    @Value("${access.token.expiration.seconds}")
    private Long accessTokenExpirationSec;

    public boolean validateToken(JwtToken jwtToken, UserDetails userDetails) {
        return (!isNotExpired(jwtToken) && jwtToken.getSubject().equals(userDetails.getUsername()));
    }

    private boolean isNotExpired(JwtToken token) {
        return token.getExpirationDate().before(new Date());
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationSec * 1000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtToken parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String subject = claims.getSubject();
        Date expirationDate = claims.getExpiration();

        return new JwtToken(subject, expirationDate, token);
    }
}
