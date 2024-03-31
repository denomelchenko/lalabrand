package com.lalabrand.ecommerce.security.refresh_token;

import com.lalabrand.ecommerce.user.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.token.expiration.seconds}")
    private Integer refreshTokenExpirationSec;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiresAt(Instant.now().plusSeconds(refreshTokenExpirationSec))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (isTokenExpired(token)) {
            refreshTokenRepository.delete(token);
            String message = String.format("Refresh token %s is expired. Please log in again.", token.getToken());
            logger.error(message);
            throw new RuntimeException(message);
        }
        return token;
    }

    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiresAt().compareTo(Instant.now()) < 0;
    }
}
