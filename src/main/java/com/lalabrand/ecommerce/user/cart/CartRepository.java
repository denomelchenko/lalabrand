package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByUserId(Integer userId);
}
