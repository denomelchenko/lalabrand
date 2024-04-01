package com.lalabrand.ecommerce.item.look;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LookRepository extends JpaRepository<Look, String> {
    Optional<Look> findFirstByIdGreaterThan(String id);

    Optional<Look> findFirstByOrderByIdAsc();

}
