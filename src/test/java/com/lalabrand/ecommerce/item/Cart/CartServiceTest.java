package com.lalabrand.ecommerce.item.Cart;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.ItemDto;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.enums.SizeType;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.user.cart.Cart;
import com.lalabrand.ecommerce.user.cart.CartDto;
import com.lalabrand.ecommerce.user.cart.CartRepository;
import com.lalabrand.ecommerce.user.cart.CartService;

import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {
    private CartRepository cartRepository;

    private CartService cartService;

    private static Cart cartWithItems;

    @BeforeAll
    public static void beforeAll() {
        Item item = new Item();

        cartWithItems = new Cart();
        item.setId(1);
        item.setTitle("Stylish Backpack");
        item.setShortDisc("Perfect for everyday adventures.");
        item.setLongDisc("This versatile backpack features spacious compartments, padded straps for comfort, and a sleek design.");
        item.setRating(new BigDecimal("4.8"));
        item.setPrice(new BigDecimal("59.99"));
        item.setCurrency("USD");
        item.setAvailableCount(15);
        item.setSalePrice(new BigDecimal("12"));
        item.setImage("path/to/backpack_image.jpg");
        item.setSoldCount(2);
        item.setCreatedAt(Instant.now());
        Cart cart = new Cart();
        cart.setId(1);
        Set<CartItem> cartItems = new HashSet<>();

        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setItem(item); // Set the associated Item
        itemInfo.setColor(ColorEnum.BLUE); // Assuming you have a ColorEnum defined
        itemInfo.setImage("path/to/blue_item_image.jpg");

        Size size = new Size();
        size.setId(1);
        size.setValue("XL");
        size.setSizeType(SizeType.CLOTHES);

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setItemInfo(itemInfo);
        cartItems.add(cartItem);
        cartItem.setSize(size);
        cartWithItems.setCartItems(cartItems);
    }

    @BeforeEach
    public void beforeEach() {
        cartRepository = mock(CartRepository.class);
        cartService = new CartService(cartRepository);
    }

    // Should return CartDto when userId is valid and cart exists
    @Test
    public void test_return_cartDto_when_userId_valid_and_cart_exists() {
        Integer userId = 1;

        when(cartRepository.findCartByUserId(1)).thenReturn(Optional.of(cartWithItems));

        // Act
        CartDto result = cartService.findCartByUserId(userId).get();

        // Assert
        assertNotNull(result);
        assertEquals(cartWithItems.getId(), result.getId());
        assertEquals(cartWithItems.getCartItems().size(), result.getCartItems().size());
    }

    // Should throw IllegalArgumentException when userId is null
    @Test
    public void test_throw_IllegalArgumentException_when_userId_null() {
        // Arrange
        Integer userId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> cartService.findCartByUserId(userId));
    }

    // Should return empty CartDto when cart exists but has no cartItems
    @Test
    public void test_return_empty_CartDto_when_cart_exists_but_has_no_cartItems() {
        // Arrange
        Integer userId = 1;
        Cart cart = new Cart();
        cart.setId(1);
        cart.setCartItems(new HashSet<>());
        Optional<Cart> optionalCart = Optional.of(cart);
        when(cartRepository.findCartByUserId(userId)).thenReturn(optionalCart);

        // Act
        CartDto result = cartService.findCartByUserId(userId).get();

        // Assert
        assertNotNull(result);
        assertEquals(cart.getId(), result.getId());
        assertEquals(0, result.getCartItems().size());
    }

    // Should return empty CartDto when cart does not exist for given userId
    @Test
    public void test_return_empty_CartDto_when_cart_not_exist_for_userId() {
        // Arrange
        Integer userId = 1;
        when(cartRepository.findCartByUserId(userId)).thenReturn(Optional.empty());

        // Act
        Optional<CartDto> result = cartService.findCartByUserId(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(result, Optional.empty());
    }

    // Should handle case when CartRepository returns null instead of Optional.empty()
    @Test
    public void test_handle_case_when_CartRepository_returns_null_instead_of_Optional_empty() {
        // Arrange
        Integer userId = 1;
        when(cartRepository.findCartByUserId(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> cartService.findCartByUserId(userId));
    }

    // Should handle case when CartRepository throws exception
    @Test
    public void test_handle_case_when_CartRepository_throws_exception() {
        // Arrange
        Integer userId = 1;
        when(cartRepository.findCartByUserId(userId)).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartService.findCartByUserId(userId));
    }

}