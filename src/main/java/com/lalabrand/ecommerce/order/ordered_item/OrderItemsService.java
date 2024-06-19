package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;

import java.util.Set;

public interface OrderItemsService {
    void addOrderedProduct(OrderedItem orderItem);
    Set<OrderedItem> getByAllByOrderId(String orderId);
    OrderedItem generateOrderedFromCartItem(Order order, CartItem cartItem);
}
