package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.item.item_comment.ItemComment;
import com.lalabrand.ecommerce.order.Order;
import com.lalabrand.ecommerce.user.address.Address;
import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.user.role.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ItemComment> itemComments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Order> orders;

    @Column(name = "bonus", nullable = false)
    private Integer bonus;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;

    @Column(name = "language", columnDefinition = "ENUM('UA', 'EN')")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "password_version", nullable = false)
    private Integer passwordVersion;

    public User(String email, String password, Integer passwordVersion) {
        this.email = email;
        this.password = password;
        this.passwordVersion = passwordVersion;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
