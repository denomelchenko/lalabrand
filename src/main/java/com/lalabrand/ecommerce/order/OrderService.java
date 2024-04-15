package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.shipping.ShippingInfo;
import com.lalabrand.ecommerce.order.shipping.ShippingInfoDTO;

import java.util.List;

public interface OrderService {
    void placeOrder(String userId, ShippingInfo shippingInfo);
    List<Order> getAll(String userId);
    void delete(String orderId);
}
