package com.lalabrand.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.enums.Status;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfo;
import com.lalabrand.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_number", nullable = false)
    private Long orderNumber;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private Set<OrderedItem> orderedItems;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "shipping_fee", precision = 10, nullable = false)
    private Float shippingFee;

    @Column(name = "discount", precision = 10)
    private Float discount;

    @Column(name = "tax", precision = 10, nullable = false)
    private Float tax;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELED')")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "currency", nullable = false, columnDefinition = "ENUM('UAH','EUR','USD')")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "shipping_id")
    @JsonManagedReference
    private ShippingInfo shipping;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
