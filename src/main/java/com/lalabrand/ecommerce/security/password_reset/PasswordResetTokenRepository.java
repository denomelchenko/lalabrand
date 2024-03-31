package com.lalabrand.ecommerce.security.password_reset;

import com.lalabrand.ecommerce.user.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByTokenAndUser(@Size(max = 8) @NotNull String token, @NotNull User user);
}
