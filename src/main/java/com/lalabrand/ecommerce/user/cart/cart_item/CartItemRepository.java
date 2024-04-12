package com.lalabrand.ecommerce.user.cart.cart_item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAll();
}
