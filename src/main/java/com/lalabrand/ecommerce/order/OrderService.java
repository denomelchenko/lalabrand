package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.utils.CommonResponse;

import java.util.List;

public interface OrderService {
    CommonResponse placeOrder(String userId, ShippingInfoRequest shippingInfoRequest);
    List<Order> getAll(String userId);
    CommonResponse delete(String orderId);
}
