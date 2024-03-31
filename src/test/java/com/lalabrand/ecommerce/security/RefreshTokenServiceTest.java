package com.lalabrand.ecommerce.auth;

import com.lalabrand.ecommerce.auth.refresh_token.RefreshToken;
import com.lalabrand.ecommerce.auth.refresh_token.RefreshTokenRepository;
import com.lalabrand.ecommerce.auth.refresh_token.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshTokenService refreshTokenService;

    @BeforeEach
    public void setUp() {
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        refreshTokenService = new RefreshTokenService(refreshTokenRepository);
    }


    @Test
    public void testVerifyExpiration_NotExpired() {
        RefreshToken token = new RefreshToken();
        token.setExpiresAt(Instant.now().plusSeconds(3600)); // Токен не истек

        RefreshToken verifiedToken = refreshTokenService.verifyExpiration(token);

        assertEquals(token, verifiedToken);
    }

    @Test
    public void testVerifyExpiration_Expired() {
        RefreshToken token = new RefreshToken();
        token.setToken("expired_token");
        token.setExpiresAt(Instant.now().minusSeconds(3600));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> refreshTokenService.verifyExpiration(token));

        assertEquals("expired_token Refresh token is expired. Please log in again.", exception.getMessage());
    }
}
