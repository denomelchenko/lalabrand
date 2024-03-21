package com.lalabrand.ecommerce.order;

import com.lalabrand.ecommerce.item.enums.Currency;
import com.lalabrand.ecommerce.order.shipping.ShippingInfo;
import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "order")
    private Set<OrderedItem> orderedItems;

    @Column(name = "total_price", precision = 10)
    private BigDecimal totalPrice;

    @Column(name = "shipping_fee", precision = 10)
    private BigDecimal shippingFee;

    @Column(name = "discount", precision = 10)
    private BigDecimal discount;

    @Column(name = "tax", precision = 10)
    private BigDecimal tax;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "shipping_id")
    private ShippingInfo shipping;

}
