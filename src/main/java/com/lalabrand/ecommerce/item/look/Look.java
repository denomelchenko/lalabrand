package com.lalabrand.ecommerce.item.look;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "look")
public class Look {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, columnDefinition = "ENUM('MAN','WOMAN','UNISEX')")
    private Gender gender;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToMany
    @JoinTable(name = "look_item",
            joinColumns = @JoinColumn(name = "look_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items = new LinkedHashSet<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
