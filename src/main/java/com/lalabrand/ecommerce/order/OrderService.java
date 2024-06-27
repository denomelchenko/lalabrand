package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.utils.CommonResponse;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    CommonResponse placeOrder(String userId, ShippingInfoRequest shippingInfoRequest, Currency currency);
    List<Order> getAllByUserId(String userId);
    List<Order> getAllByStatus(Status status);
    CommonResponse delete(String orderId);
    BigDecimal calculateTotal(String userId);
    BigDecimal calculateDiscount(String userId);
}
