package com.lalabrand.ecommerce.user.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Security;

@Controller
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @QueryMapping(name = "cartByUserId")
    @PreAuthorize("hasAuthority('USER')")
    public CartDTO findCartByUserId(@Argument Integer userId) {
        return cartService.findCartByUserId(userId).orElse(null);
    }
}
