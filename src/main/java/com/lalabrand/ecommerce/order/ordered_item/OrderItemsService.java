package com.lalabrand.ecommerce.order.ordered_item;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderItemsService {
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemsService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public void addOrderedProducts(OrderedItem orderItem) {
        orderItemsRepository.save(orderItem);
    }
    public Set<OrderedItem> getByAllOrderId(String orderId) {
        return orderItemsRepository.findAllByOrderId(orderId);
    }
}
