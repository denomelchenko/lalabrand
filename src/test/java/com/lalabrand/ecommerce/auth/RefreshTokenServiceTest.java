package com.lalabrand.ecommerce.auth;

import com.lalabrand.ecommerce.user.UserRepository;
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
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        userRepository = mock(UserRepository.class);
        refreshTokenService = new RefreshTokenService(refreshTokenRepository, userRepository);
    }


    @Test
    public void testVerifyExpiration_NotExpired() {
        RefreshToken token = new RefreshToken();
        token.setExpiryDate(Instant.now().plusSeconds(3600)); // Токен не истек

        RefreshToken verifiedToken = refreshTokenService.verifyExpiration(token);

        assertEquals(token, verifiedToken);
    }

    @Test
    public void testVerifyExpiration_Expired() {
        RefreshToken token = new RefreshToken();
        token.setToken("expired_token");
        token.setExpiryDate(Instant.now().minusSeconds(3600));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> refreshTokenService.verifyExpiration(token));

        assertEquals("expired_token Refresh token is expired. Please make a new login..!", exception.getMessage());
    }
}
