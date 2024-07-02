package com.lalabrand.ecommerce.order.ordered_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderedItem, String> {
    @Query("SELECT oi FROM OrderedItem oi WHERE oi.order.id = :orderId")
    Set<OrderedItem> findAllByOrderId(@Param("orderId") String orderId);
}
