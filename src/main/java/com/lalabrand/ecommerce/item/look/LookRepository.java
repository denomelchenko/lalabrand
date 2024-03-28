package com.lalabrand.ecommerce.item.look;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface LookRepository extends JpaRepository<Look, String> {
    Optional<Look> findFirstByOrderByCreatedAt();
    Optional<Look> findById(String id);
    Optional<Look> findFirstByCreatedAtAfterOrderByCreatedAt(Instant date);
}
