package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.order.OrderRepository;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.utils.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
@Transactional
public class OrderItemsServiceImpl implements OrderItemsService{
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public OrderItemsServiceImpl(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public void addOrderedProducts(OrderedItem orderItem) {
        orderItemsRepository.save(orderItem);
    }
    public Set<OrderedItem> getByAllByOrderId(String orderId) {
        return orderItemsRepository.findAllByOrderId(orderId);
    }
}
