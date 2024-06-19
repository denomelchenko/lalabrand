package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryMapping(name = "orders")
    @PreAuthorize("hasAuthority('USER')")
    public List<Order> getAllOrders() {
        return orderService.getAll(CommonUtils.getCurrentUserId());
    }

    @MutationMapping(name = "deleteOrderById")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CommonResponse deleteOrderById(@Argument("orderId") String orderId) {
           return orderService.delete(orderId);
    }
}
