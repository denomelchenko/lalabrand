package com.lalabrand.ecommerce.user.cart;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private String userId;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    public Cart( String userId) {
        this.userId = userId;
    }
}
