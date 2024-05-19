package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.paypal.base.rest.PayPalRESTException;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    CommonResponse placeOrder(String userId, ShippingInfoRequest shippingInfoRequest, Currency currency);
    List<Order> getAll(String userId);
    CommonResponse delete(String orderId);
}
