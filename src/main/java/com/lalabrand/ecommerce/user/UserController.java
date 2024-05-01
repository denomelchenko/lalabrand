package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.security.AuthRequestDTO;
import com.lalabrand.ecommerce.security.JwtResponseDTO;
import com.lalabrand.ecommerce.security.jwt_token.JwtService;
import com.lalabrand.ecommerce.security.refresh_token.RefreshToken;
import com.lalabrand.ecommerce.security.refresh_token.RefreshTokenRequestDTO;
import com.lalabrand.ecommerce.security.refresh_token.RefreshTokenService;
import com.lalabrand.ecommerce.utils.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.nio.file.AccessDeniedException;

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
    public UserDTO saveUser(@Validated @Argument UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @MutationMapping(name = "login")
    public JwtResponseDTO loginUserAndGetTokens(@Argument @Valid AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            User user = userService.findByEmail(authRequest.getEmail()).get();
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(
                    user
            );
            return JwtResponseDTO.builder()
                    .refreshToken(refreshToken.getToken())
                    .accessToken(jwtService.generateToken(
                            authRequest.getEmail(),
                            refreshToken.getUser().getId(),
                            user.getPasswordVersion()
                    ))
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid request");
        }
    }

    @MutationMapping(name = "refreshToken")
    public JwtResponseDTO refreshAccessToken(@Argument @Valid RefreshTokenRequestDTO refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(
                            user.getEmail(),
                            user.getId(),
                            user.getPasswordVersion()
                    );
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh token is not valid"));
    }

    @MutationMapping(name = "updateUser")
    @PreAuthorize("hasAuthority('USER')")
    public UserDTO updateUser(@Argument(name = "userUpdateInput") @Valid UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest);
    }
}
