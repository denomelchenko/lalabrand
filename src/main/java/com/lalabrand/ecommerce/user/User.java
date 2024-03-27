package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.item.item_comment.ItemComment;
import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.user.address.Address;
import com.lalabrand.ecommerce.user.cart.Cart;
import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.user.role.UserRole;
import com.lalabrand.ecommerce.user.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Clock;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses;

    @OneToMany(mappedBy = "user")
    private Set<Cart> carts;

    @OneToMany(mappedBy = "user")
    private Set<ItemComment> itemComments;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<Wishlist> wishlists;

    @Column(name = "bonus")
    private Integer bonus;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new LinkedHashSet<>();

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    public User(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
            System.out.println(Instant.now());
        }
    }
}
