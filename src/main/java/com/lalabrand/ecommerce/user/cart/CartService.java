package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemRepository;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }


    public Optional<CartDTO> findCartByUserId(String userId) {
        if (CommonUtils.isIdInValid(userId)) {
            throw new IllegalArgumentException("UserId is not valid");
        }
        Optional<Cart> cart = cartRepository.findCartByUserId(userId);
        if (cart.isEmpty() || cart.get().getCartItems().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(CartDTO.fromEntity(cart.get()));
    }

    public void deleteCartItems(){
        cartItemRepository.deleteAll();
    }
}
