package com.lalabrand.ecommerce.order.ordered_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderedItem, Long> {
}
