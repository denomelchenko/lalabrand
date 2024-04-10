package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.Set;

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
        UserDetailsImpl user = commonUtils.getCurrentUser();
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return cartService.findCartByUserId(user.getId()).orElse(null);
    }

    @MutationMapping(name = "itemToWishlist")
    @PreAuthorize("hasAuthority('USER')")
    public CartDTO addItemToWishlist(@Argument String itemInfoId, @Argument String sizeId, @Argument String itemId) {
        return cartService.addItemToCart(itemId, itemInfoId, sizeId, commonUtils.getCurrentUser().getId());
    }
}
