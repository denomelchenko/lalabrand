package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.item.item_comment.ItemComment;
import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.user.address.Address;
import com.lalabrand.ecommerce.user.cart.Cart;
import com.lalabrand.ecommerce.user.role.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Cart> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ItemComment> itemComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Roles> roles = new LinkedHashSet<>();

}