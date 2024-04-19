package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Cart}
 */
@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
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

    public Cart toEntity(User user) {
        Set<CartItem> cartItemEntities = new HashSet<>();
        for (CartItemDTO cartItemDTO : this.cartItems) {
            cartItemEntities.add(cartItemDTO.toEntity());
        }
        return Cart.builder()
                .id(this.id)
                .user(user)
                .cartItems(cartItemEntities)
                .totalCost(this.totalCost)
                .build();
    }
}
