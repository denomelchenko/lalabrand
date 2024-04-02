package com.lalabrand.ecommerce.security.password_reset;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserRepository;
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

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {
    private final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    @Value("${password.reset.token.symbols}")
    private String passwordResetTokenSymbols;
    @Value("${reset.password.token.expiration.seconds}")
    private Integer resetPasswordExpiration;


    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, UserService userService,
                                UserRepository userRepository, PasswordEncoder passwordEncoder,
                                EmailSenderService emailSenderService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    public boolean resetPasswordForUser(PasswordResetRequest passwordResetInput) throws AccessDeniedException {
        Optional<User> user = userService.findByEmail(passwordResetInput.getEmail());
        if (user.isPresent()) {
            Optional<PasswordResetToken> resetToken = passwordResetTokenRepository
                    .findByTokenAndUser(passwordResetInput.getToken(), user.get());
            if (resetToken.isPresent()) {
                updateUserPassword(new UserRequest(passwordResetInput.getPassword(),
                        passwordResetInput.getEmail(),
                        user.get().getId()));
                logger.info("Password reset successfully for user with email: {}", passwordResetInput.getEmail());
                return true;
            }
        }
        logger.error("Token is not valid for user with email: {}", passwordResetInput.getEmail());
        throw new AccessDeniedException("Token is not valid");
    }

    private void updateUserPassword(UserRequest userRequest) {
        String password = passwordEncoder.encode(userRequest.getPassword());
        userRepository.save(new User(userRequest.getId(), userRequest.getEmail(), password));
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
        return passwordResetTokenRepository.save(PasswordResetToken.builder()
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
