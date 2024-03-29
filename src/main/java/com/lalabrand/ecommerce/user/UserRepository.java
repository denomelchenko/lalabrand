package com.lalabrand.ecommerce.user;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    @NonNull Optional<User> findById(@NonNull String id);
}
