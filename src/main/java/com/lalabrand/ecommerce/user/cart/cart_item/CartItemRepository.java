package com.lalabrand.ecommerce.user.cart.cart_item;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    CartItem findCartItemByCartIdAndItemIdAndItemInfoIdAndSizeId(String cartId, String itemId, String itemInfoId, String sizeId);
    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cartId = :cartId")
    void deleteAllByCartId(String cartId);
}
