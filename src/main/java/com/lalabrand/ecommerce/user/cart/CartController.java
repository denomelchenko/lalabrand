package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CartDTO findCartByUserId() {
        Optional<User> user = commonUtils.getCurrentUser();
        if (user.isPresent()) {
            return cartService.findCartByUserId(user.get().getId()).orElse(null);
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
