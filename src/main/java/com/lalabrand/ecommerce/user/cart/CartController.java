package com.lalabrand.ecommerce.user.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @QueryMapping(name = "cartByUserId")
    public CartDto findCartByUserId(@Argument Integer userId) {
        return cartService.findCartItemsByUserId(userId);
        //TODO: Add security here
    }
}