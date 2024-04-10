package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.utils.CommonUtils;
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

    public Optional<CartDTO> findCartByUserId(String userId) {
        if (CommonUtils.isIdInvalid(userId)) {
            throw new IllegalArgumentException("UserId is not valid");
        }
        Optional<Cart> cart = cartRepository.findCartByUserId(userId);
        if (cart.isEmpty() || cart.get().getCartItems().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CartDTO.fromEntity(cart.get()));
    }
}
