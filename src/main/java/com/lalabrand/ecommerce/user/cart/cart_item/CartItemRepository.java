package com.lalabrand.ecommerce.user.cart.cart_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    CartItem findCartItemByCartIdAndItemIdAndItemInfoIdAndSizeId(String cartId, String itemId, String itemInfoId, String sizeId);
}
