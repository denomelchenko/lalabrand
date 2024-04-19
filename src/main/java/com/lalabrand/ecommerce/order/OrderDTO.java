package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.item.enums.Currency;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.order.ordered_item.OrderItemsDTO;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfo;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoDTO;
import com.lalabrand.ecommerce.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Value
@Builder
public class OrderDTO implements Serializable {
    private String id;
    private String userId;
    private BigDecimal tax;
    private Currency currency;
    private Status status;
    private BigDecimal shippingFee;
    private BigDecimal totalPrice;
    private ShippingInfoDTO shippingInfoDTO;
    private Set<OrderItemsDTO> orderedItems;

    public static OrderDTO fromEntity(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .tax(order.getTax())
                .currency(order.getCurrency())
                .status(order.getStatus())
                .shippingFee(order.getShippingFee())
                .totalPrice(order.getTotalPrice())
                .shippingInfoDTO(ShippingInfoDTO.fromEntity(order.getShipping()))
                .orderedItems(order.getOrderedItems().stream().map(OrderItemsDTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }

    public Order toEntity(ShippingInfo shippingInfo, User user) {
        return Order.builder()
                .id(this.getId())
                .tax(this.getTax())
                .currency(this.getCurrency())
                .status(this.getStatus())
                .shippingFee(this.getShippingFee())
                .totalPrice(this.getTotalPrice())
                .user(user)
                .shipping(shippingInfo)
                .orderedItems(this.getOrderedItems().stream().map(OrderItemsDTO::toEntity).collect(Collectors.toSet()))
                .build();
    }

}
