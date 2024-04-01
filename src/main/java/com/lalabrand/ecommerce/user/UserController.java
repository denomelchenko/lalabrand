package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.auth.*;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @MutationMapping(name = "user")
    public UserResponse saveUser(@Validated @Argument UserRequest userRequest) {
        try {
            return userService.saveUser(userRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @MutationMapping(name = "login")
    public JwtResponseDTO loginUserAndGetTokens(@Argument AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(
                    userService.findByEmail(authRequest.getEmail()).get()
            );
            return JwtResponseDTO.builder()
                    .refreshToken(refreshToken.getToken())
                    .accessToken(jwtService.generateToken(
                            authRequest.getEmail(),
                            refreshToken.getUser().getId())
                    )
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid request");
        }
    }

    @MutationMapping(name = "refreshToken")
    public JwtResponseDTO refreshAccessToken(@Argument RefreshTokenRequestDTO refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getEmail(), user.getId());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh token is not valid"));
    }
}
