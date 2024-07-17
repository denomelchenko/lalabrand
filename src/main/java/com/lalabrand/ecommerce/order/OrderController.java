package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
import com.lalabrand.ecommerce.utils.annotation.Id;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryMapping(name = "ordersByUserId")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Order> getAllOrdersByUserId() {
        return orderService.getAllByUserId(CommonUtils.getCurrentUserId());
    }

    @QueryMapping(name = "ordersByStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Order> getAllOrdersByStatus(@Argument("status") Status status) {
        return orderService.getAllByStatus(status);
    }

    @MutationMapping(name = "deleteOrderById")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CommonResponse deleteOrderById(@Argument("orderId") @Id String orderId) {
        return orderService.delete(orderId);
    }
}
