package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Cart}
 */
@Value
@Builder
public class CartDTO implements Serializable {
    Integer id;
    Set<CartItemDTO> cartItems;

    public static CartDTO fromEntity(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .cartItems(cart.getCartItems().stream().map(CartItemDTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
