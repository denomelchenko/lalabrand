package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDto;
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
public class CartDto implements Serializable {
    Integer id;
    Set<CartItemDto> cartItems;

    public static CartDto fromEntity(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .cartItems(cart.getCartItems().stream().map(CartItemDto::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}