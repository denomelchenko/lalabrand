package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Cart}
 */
@Value
@Builder
public class CartDTO implements Serializable {
    String id;
    Set<CartItemDTO> cartItems;
    BigDecimal totalCost;

    public static CartDTO fromEntity(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .cartItems(cart.getCartItems().stream().map(CartItemDTO::fromEntity).collect(Collectors.toSet()))
                .totalCost(cart.getCartItems().stream()
                        .map(cartItem -> cartItem.getItem().getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }
}
