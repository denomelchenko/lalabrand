package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class OrderItemsServiceImpl implements OrderItemsService {
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemsServiceImpl(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public void addOrderedProduct(OrderedItem orderItem) {
        orderItemsRepository.save(orderItem);
    }

    public Set<OrderedItem> getByAllByOrderId(String orderId) {
        return orderItemsRepository.findAllByOrderId(orderId);
    }

    public OrderedItem generateOrderedFromCartItem(Order order, CartItem cartItem) {
        return OrderedItem.builder()
                .order(order)
                .item(cartItem.getItem())
                .title(cartItem.getItem().getTitle())
                .color(String.valueOf(cartItem.getItemInfo().getColor()))
                .image(cartItem.getItemInfo().getImage())
                .sizeType(cartItem.getSize().getSizeType())
                .count(cartItem.getCount())
                .price(cartItem.getItem().getPrice() * cartItem.getCount())
                .build();
    }
}
