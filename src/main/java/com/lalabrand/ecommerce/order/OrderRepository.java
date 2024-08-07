package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.order.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteById(String orderId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Order> findAllByUserIdOrderByCreatedAtDesc(String userId);

    @Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.createdAt ASC")
    List<Order> findAllByStatusOrderByCreatedAtAsc(Status status);
}
