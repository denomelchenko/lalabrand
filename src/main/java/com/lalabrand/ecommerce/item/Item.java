package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.category.Category;
import com.lalabrand.ecommerce.item.item_comment.ItemComment;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.look.Look;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "short_disc", nullable = false, length = 128)
    private String shortDisc;

    @Column(name = "long_disc")
    private String longDisc;

    @Column(name = "rating", precision = 10)
    private Float rating;

    @Column(name = "price", nullable = false, precision = 10)
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
    private Category category;

    @Column(name = "category_id", nullable = false, length = 36)
    private String categoryId;

    @Column(name = "available_count", nullable = false)
    private Integer availableCount;

    @Column(name = "sale_price")
    private Float salePrice;

    @Column(name = "sold_count", nullable = false)
    private Integer soldCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "item")
    private Set<ItemComment> itemComments;

    @OneToMany(mappedBy = "item")
    private Set<OrderedItem> orderedItems;

    @ManyToMany(mappedBy = "items")
    private Set<Look> looks;

    @OneToMany(mappedBy = "item")
    private Set<ItemInfo> itemInfos;

    public Item(String id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}