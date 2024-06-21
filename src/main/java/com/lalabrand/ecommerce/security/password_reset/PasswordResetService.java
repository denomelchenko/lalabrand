package com.lalabrand.ecommerce.security.password_reset;

import com.lalabrand.ecommerce.exception.AccessDeniedException;
import com.lalabrand.ecommerce.security.refresh_token.RefreshTokenService;
import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserRequest;
import com.lalabrand.ecommerce.user.UserService;
import com.lalabrand.ecommerce.utils.EmailSenderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {
    private final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final RefreshTokenService refreshTokenService;
    @Value("${password.reset.token.symbols}")
    private String passwordResetTokenSymbols;
    @Value("${reset.password.token.expiration.seconds}")
    private Integer resetPasswordExpiration;


    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository,
                                UserService userService, PasswordEncoder passwordEncoder,
                                EmailSenderService emailSenderService, RefreshTokenService refreshTokenService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public boolean resetPasswordForUser(PasswordResetRequest passwordResetInput) {
        Optional<User> user = userService.findByEmail(passwordResetInput.getEmail());
        if (user.isPresent()) {
            Optional<PasswordResetToken> resetToken = passwordResetTokenRepository
                    .findByTokenAndUser(passwordResetInput.getToken(), user.get());
            if (resetToken.isPresent()) {
                if (passwordEncoder.matches(passwordResetInput.getPassword(), user.get().getPassword())) {
                    logger.error("Password the same with exist password for this user");
                    throw new IllegalArgumentException("Password the same with exist password for this user");
                }
                updateUserPassword(new UserRequest(
                                passwordResetInput.getPassword(),
                                passwordResetInput.getEmail()
                        ),
                        user.get().getPasswordVersion() + 1,
                        user.get().getId());
                logger.info("Password reset successfully for user with email: {}", passwordResetInput.getEmail());
                passwordResetTokenRepository.deleteById(resetToken.get().getId());
                refreshTokenService.deleteTokenByUserId(user.get().getId());
                return true;
            }
            logger.error("Token is not valid for user with email: {}", passwordResetInput.getEmail());
            throw new AccessDeniedException("Token is not valid");
        }
        logger.error("User with email: {} does not exist", passwordResetInput.getEmail());
        throw new IllegalArgumentException("User does not exist");
    }

    @Transactional
    protected void updateUserPassword(UserRequest userRequest, Integer passwordVersion, String userId) {
        String password = passwordEncoder.encode(userRequest.getPassword());
        userService.updatePasswordForUser(new User(userRequest.getEmail(), password, passwordVersion), password, userId);
    }

    @Transactional
    public boolean sendPasswordResetTokenByEmail(String email) {
        User user = userService.findByEmail(email).orElseThrow(() -> {
            logger.error("User with email {} does not exist", email);
            return new EntityNotFoundException("User with email " + email + " does not exist");
        });

        PasswordResetToken passwordResetToken = createPasswordResetToken(user);

        String subject = "Password reset for lalabrand";
        String message = "To reset your password, please write this code on your lalabrand reset password page:\n"
                + passwordResetToken.getToken();

        emailSenderService.sendEmail(email, subject, message);
        logger.info("Password reset token sent successfully to user with email: {}", email);
        return true;
    }

    private PasswordResetToken createPasswordResetToken(User user) {
        return passwordResetTokenRepository.save(
                PasswordResetToken.builder()
                        .token(generatePasswordResetToken())
                        .user(user)
                        .expiresAt(Instant.now().plusSeconds(resetPasswordExpiration))
                        .build());
    }

    private String generatePasswordResetToken() {
        int length = 6;
        Random random = new Random();
        StringBuilder tokenBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            tokenBuilder.append(passwordResetTokenSymbols.charAt(random.nextInt(passwordResetTokenSymbols.length())));
        }
        return tokenBuilder.toString();
    }
}
