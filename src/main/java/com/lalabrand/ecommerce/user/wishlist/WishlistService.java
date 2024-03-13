package com.lalabrand.ecommerce.user.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Optional<WishlistDto> findWishlistByUserId(Integer userId) {
        return wishlistRepository.findWishlistByUserId(userId).map(WishlistDto::fromEntity);
    }
}
