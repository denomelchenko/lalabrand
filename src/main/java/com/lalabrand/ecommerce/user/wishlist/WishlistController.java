package com.lalabrand.ecommerce.user.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class WishlistController {
    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @QueryMapping(name = "wishlistByUserId")
    @PreAuthorize("hasAuthority('USER')")
    public WishlistDTO findWishlistByUserId(@Argument Integer userId) {
        return wishlistService.findWishlistByUserId(userId).orElse(null);
    }
}
