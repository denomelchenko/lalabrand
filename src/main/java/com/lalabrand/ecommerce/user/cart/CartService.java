package com.lalabrand.ecommerce.user.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Optional<CartDTO> findCartByUserId(final Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        Optional<Cart> cart = cartRepository.findCartByUserId(userId);
        if (cart.isEmpty() || cart.get().getCartItems().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CartDTO.fromEntity(cart.get()));
    }
}
