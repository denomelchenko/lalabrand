package com.lalabrand.ecommerce.security.jwt_token;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtService {
    @Value("${secret}")
    private String key;

    private byte[] decodedKey;

    @Value("${access.token.expiration.seconds}")
    private Long accessTokenExpirationSec;

    public boolean validateToken(JwtPayload jwtPayload, UserDetailsImpl userDetails) {
        return !isNotExpired(jwtPayload)
                && jwtPayload.getId().equals(userDetails.getId())
                && jwtPayload.getEmail().equals(userDetails.getUsername())
                && Objects.equals(jwtPayload.getPasswordVersion(), userDetails.getPasswordVersion());
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

    public String generateToken(String email, String id, Integer passwordVersion) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("passwordVersion", passwordVersion);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String email) {
        Instant currentDate = Clock.systemUTC().instant();
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(Date.from(currentDate))
                .expiration(Date.from(currentDate.plusSeconds(accessTokenExpirationSec)))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    public JwtPayload parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String id = (String) claims.get("id");
        Integer passwordVersion = (Integer) claims.get("passwordVersion");
        String email = claims.getSubject();
        Date expirationDate = claims.getExpiration();

        return new JwtPayload(id, email, passwordVersion, expirationDate);
    }
}
