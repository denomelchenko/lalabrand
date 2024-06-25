package com.lalabrand.ecommerce.user.wishlist;

import com.lalabrand.ecommerce.utils.CommonUtils;
import com.lalabrand.ecommerce.utils.annotation.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class WishlistController {
    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @QueryMapping(name = "wishlist")
    @PreAuthorize("hasAuthority('USER')")
    public WishlistDTO findWishlistForCurrentUser() {
        return wishlistService.findWishlistByUserId(CommonUtils.getCurrentUserId()).orElse(null);

    }

    @MutationMapping(name = "itemToWishlist")
    @PreAuthorize("hasAuthority('USER')")
    public WishlistDTO addItemToWishlist(@Argument @Id String itemId) {
        return wishlistService.addItemToWishlist(itemId, CommonUtils.getCurrentUserId());
    }

    @MutationMapping(name = "itemsToWishlist")
    @PreAuthorize("hasAuthority('USER')")
    public WishlistDTO addItemsToWishlist(@Argument Set<String> itemsIds) {
        return wishlistService.addItemsToWishlist(itemsIds, CommonUtils.getCurrentUserId());
    }
}
