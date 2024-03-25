package com.lalabrand.ecommerce.item.wishlist;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.wishlist.Wishlist;
import com.lalabrand.ecommerce.user.wishlist.WishlistDto;
import com.lalabrand.ecommerce.user.wishlist.WishlistRepository;
import com.lalabrand.ecommerce.user.wishlist.WishlistService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WishlistServiceTest {

    private static WishlistRepository wishlistRepository;

    @BeforeAll
    public static void beforeAll() {
        wishlistRepository = mock(WishlistRepository.class);
    }

    // Should find a wishlist for a given user ID
    @Test
    public void test_find_wishlist_for_user_id() {
        // Mock WishlistRepository

        // Create test data
        Integer userId = 1;
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1);
        User user = new User();
        user.setId(userId);
        wishlist.setUser(user);
        Set<Item> items = new HashSet<>();
        Item item1 = new Item();
        item1.setId(1);
        Item item2 = new Item();
        item2.setId(2);
        items.add(item1);
        items.add(item2);
        wishlist.setItems(items);
    
        // Set up mock behavior
        when(wishlistRepository.findWishlistByUserId(userId)).thenReturn(Optional.of(wishlist));
    
        // Create instance of WishlistService
        WishlistService wishlistService = new WishlistService(wishlistRepository);
    
        // Invoke the method being tested
        Optional<WishlistDto> result = wishlistService.findWishlistByUserId(userId);
    
        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(wishlist.getId(), result.get().getId());
        assertEquals(items.size(), result.get().getItems().size());
    }

    // Should return an empty optional if no wishlist is found for a given user ID
    @Test
    public void test_return_empty_optional_if_no_wishlist_found() {
        // Mock WishlistRepository
        WishlistRepository wishlistRepository = mock(WishlistRepository.class);
    
        // Create test data
        Integer userId = 1;
    
        // Set up mock behavior
        when(wishlistRepository.findWishlistByUserId(userId)).thenReturn(Optional.empty());
    
        // Create instance of WishlistService
        WishlistService wishlistService = new WishlistService(wishlistRepository);
    
        // Invoke the method being tested
        Optional<WishlistDto> result = wishlistService.findWishlistByUserId(userId);
    
        // Verify the result
        assertFalse(result.isPresent());
    }

    // Should map Wishlist entity to WishlistDto
    @Test
    public void test_map_wishlist_entity_to_dto() {
        // Mock WishlistRepository
        WishlistRepository wishlistRepository = mock(WishlistRepository.class);
    
        // Create test data
        Integer userId = 1;
        Wishlist wishlist = new Wishlist();
        wishlist.setId(1);
        User user = new User();
        user.setId(userId);
        wishlist.setUser(user);
        Set<Item> items = new HashSet<>();
        Item item1 = new Item();
        item1.setId(1);
        Item item2 = new Item();
        item2.setId(2);
        items.add(item1);
        items.add(item2);
        wishlist.setItems(items);
    
        // Set up mock behavior
        when(wishlistRepository.findWishlistByUserId(userId)).thenReturn(Optional.of(wishlist));
    
        // Create instance of WishlistService
        WishlistService wishlistService = new WishlistService(wishlistRepository);
    
        // Invoke the method being tested
        Optional<WishlistDto> result = wishlistService.findWishlistByUserId(userId);
    
        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(wishlist.getId(), result.get().getId());
        assertEquals(items.size(), result.get().getItems().size());
    }

    // Should throw a RuntimeException if an exception is caught while finding wishlist for a given user ID
    @Test
    public void test_throw_runtime_exception_if_exception_caught() {
        // Mock WishlistRepository
        WishlistRepository wishlistRepository = mock(WishlistRepository.class);
    
        // Create test data
        Integer userId = 1;
    
        // Set up mock behavior to throw an exception
        when(wishlistRepository.findWishlistByUserId(userId)).thenThrow(new RuntimeException());
    
        // Create instance of WishlistService
        WishlistService wishlistService = new WishlistService(wishlistRepository);
    
        // Invoke the method being tested and verify the exception is thrown
        assertThrows(RuntimeException.class, () -> wishlistService.findWishlistByUserId(userId));
    }

    // Should handle gracefully when wishlistRepository returns Optional.empty for a given user ID
    @Test
    public void test_handle_gracefully_when_repository_returns_null() {
        // Mock WishlistRepository
        WishlistRepository wishlistRepository = mock(WishlistRepository.class);
    
        // Create test data
        Integer userId = 1;
    
        // Set up mock behavior to return null
        when(wishlistRepository.findWishlistByUserId(userId)).thenReturn(Optional.empty());
    
        // Create instance of WishlistService
        WishlistService wishlistService = new WishlistService(wishlistRepository);
    
        // Invoke the method being tested
        Optional<WishlistDto> result = wishlistService.findWishlistByUserId(userId);
    
        // Verify the result
        assertFalse(result.isPresent());
        assertEquals(Optional.empty(), result);
    }
}
