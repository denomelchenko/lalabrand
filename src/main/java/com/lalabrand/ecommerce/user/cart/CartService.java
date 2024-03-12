package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
}
