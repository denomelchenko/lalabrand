package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.shipping.ShippingInfo;
import com.lalabrand.ecommerce.security.UserDetailsImpl;
import com.lalabrand.ecommerce.utils.CommonUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CommonUtils commonUtils;

    public OrderController(OrderService orderService, CommonUtils commonUtils) {
        this.orderService = orderService;
        this.commonUtils = commonUtils;
    }

    @QueryMapping(name = "ordersByUserId")
    @PreAuthorize("hasAuthority('USER')")
    public List<Order> getAllOrders() {
        UserDetailsImpl user = commonUtils.getCurrentUser();
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return orderService.getAll(user.getId());
    }

    @MutationMapping(name = "placeOrder")
    @PreAuthorize("hasAuthority('USER')")
    public String placeOrder(
            @Argument("shipping") ShippingInfo shippingInfo
    ) {
        UserDetailsImpl user = commonUtils.getCurrentUser();
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        try {
            orderService.placeOrder(user.getId(), shippingInfo);
            return "Order placed successfully.";
        } catch (BadCredentialsException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @MutationMapping(name = "deleteOrderById")
    @PreAuthorize("hasAuthority('USER')")
    public String deleteOrderById(@Argument("orderId") String orderId) {
        try {
            orderService.delete(orderId);
            return "Order deleted successfully.";
        } catch (Exception e) {
            return "Failed to delete order: " + e.getMessage();
        }
    }
}
