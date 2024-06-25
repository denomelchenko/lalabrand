package com.lalabrand.ecommerce.user.wishlist;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "wishlist_item",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Item> items = new LinkedHashSet<>();

    public Wishlist(String userId, Set<String> itemIds) {
        this.userId = userId;
        itemIds.forEach(itemId -> items.add(new Item(itemId)));
    }
}
