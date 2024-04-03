package com.lalabrand.ecommerce.user.wishlist;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class WishlistController {
    private final WishlistService wishlistService;
    private final CommonUtils commonUtils;

    @Autowired
    public WishlistController(WishlistService wishlistService, CommonUtils commonUtils) {
        this.wishlistService = wishlistService;
        this.commonUtils = commonUtils;
    }

    @QueryMapping(name = "wishlist")
    @PreAuthorize("hasAuthority('USER')")
    public WishlistDTO findWishlistForCurrentUser() {
        UserDetailsImpl user = commonUtils.getCurrentUser();
        if (user != null) {
            return wishlistService.findWishlistByUserId(user.getId()).orElse(null);
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
