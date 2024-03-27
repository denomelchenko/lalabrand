package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.utils.CommonUtils;
import com.lalabrand.ecommerce.utils.UserAccessChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {
    private final CartService cartService;
    private final UserAccessChecker userAccessChecker;

    @Autowired
    public CartController(CartService cartService, UserAccessChecker userAccessChecker) {
        this.cartService = cartService;
        this.userAccessChecker = userAccessChecker;
    }

    @QueryMapping(name = "cartByUserId")
    @PreAuthorize("hasAuthority('USER') and @userAccessChecker.isCurrentUserEqualsId(#userId)")
    public CartDTO findCartByUserId(@Argument Integer userId) {
        return cartService.findCartByUserId(userId).orElse(null);
    }
}
