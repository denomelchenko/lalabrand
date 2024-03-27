package com.lalabrand.ecommerce.user.wishlist;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final Logger logger = LoggerFactory.getLogger(WishlistService.class);

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Optional<WishlistDTO> findWishlistByUserId(Integer userId) {
        try {
            Optional<Wishlist> wishlist = wishlistRepository.findWishlistByUserId(userId);
            if (wishlist.isPresent() && wishlist.get().getItems() != null && !wishlist.get().getItems().isEmpty()) {
                logger.info("Wishlist found for user with ID: {}", userId);
                return wishlist.map(WishlistDTO::fromEntity);
            } else {
                logger.info("No wishlist with items found for user with ID: {}", userId);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Failed to get wishlist for user with ID: {}", userId, e);
            throw new EntityNotFoundException("Failed to find wishlist for user with ID: " + userId, e);
        }
    }
}
