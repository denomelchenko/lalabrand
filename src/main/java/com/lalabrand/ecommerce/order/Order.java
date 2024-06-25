package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.item.enums.Currency;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
import com.lalabrand.ecommerce.order.shipping.ShippingInfo;
import com.lalabrand.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "order")
    private Set<OrderedItem> orderedItems;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "shipping_fee", precision = 10, nullable = false)
    private BigDecimal shippingFee;

    @Column(name = "discount", precision = 10)
    private BigDecimal discount;

    @Column(name = "tax", precision = 10, nullable = false)
    private BigDecimal tax;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "shipping_id")
    private ShippingInfo shipping;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
