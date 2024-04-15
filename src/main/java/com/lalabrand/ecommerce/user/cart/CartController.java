package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemDTO;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItemRequest;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class CartController {
    private final CartService cartService;
    private final CommonUtils commonUtils;

    @Autowired
    public CartController(CartService cartService, CommonUtils commonUtils) {
        this.cartService = cartService;
        this.commonUtils = commonUtils;
    }

    @QueryMapping(name = "cart")
    @PreAuthorize("hasAuthority('USER')")
    public CartDTO findCartForCurrentUser() {
        return cartService.findCartByUserId(commonUtils.getCurrentUser().getId()).orElse(null);
    }

    @MutationMapping(name = "itemToCart")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse addItemToCart(@Argument @Valid CartItemRequest cartItemRequest) {
        return cartService.addItemToCart(cartItemRequest, commonUtils.getCurrentUser().getId());
    }

    @MutationMapping(name = "removeItemFromCart")
    @PreAuthorize("hasAuthority('USER')")
    public CommonResponse removeItemFromCart(@Argument @Valid CartItemRequest cartItemRequest) {
        return cartService.removeItemFromCart(cartItemRequest, commonUtils.getCurrentUser().getId());
    }
}
