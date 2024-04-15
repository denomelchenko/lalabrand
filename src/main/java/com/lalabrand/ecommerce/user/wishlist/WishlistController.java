package com.lalabrand.ecommerce.user.wishlist;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
import com.lalabrand.ecommerce.utils.CommonUtils;
import com.lalabrand.ecommerce.utils.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.Set;

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
        return wishlistService.findWishlistByUserId(commonUtils.getCurrentUser().getId()).orElse(null);

    }

    @MutationMapping(name = "itemToWishlist")
    @PreAuthorize("hasAuthority('USER')")
    public WishlistDTO addItemToWishlist(@Argument @Id String itemId) {
        return wishlistService.addItemToWishlist(itemId, commonUtils.getCurrentUser().getId());
    }

    @MutationMapping(name = "itemsToWishlist")
    @PreAuthorize("hasAuthority('USER')")
    public WishlistDTO addItemsToWishlist(@Argument Set<String> itemsIds) {
        return wishlistService.addItemsToWishlist(itemsIds, commonUtils.getCurrentUser().getId());
    }
}
