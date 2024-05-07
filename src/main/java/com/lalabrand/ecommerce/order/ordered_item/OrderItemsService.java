package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.utils.CommonResponse;

import java.util.Set;

public interface OrderItemsService {
    void addOrderedProducts(OrderedItem orderItem);
    Set<OrderedItem> getByAllByOrderId(String orderId);
}
