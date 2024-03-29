package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.category.Category;
import com.lalabrand.ecommerce.item.item_comment.ItemComment;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.look.Look;
import com.lalabrand.ecommerce.item.size.Size;
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
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "short_disc", nullable = false, length = 128)
    private String shortDisc;

    @Column(name = "long_disc")
    private String longDisc;

    @Column(name = "rating", precision = 10)
    private BigDecimal rating;

    @Column(name = "price", nullable = false, precision = 10)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "available_count", nullable = false)
    private Integer availableCount;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "image", nullable = false)
    private String image;

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

    @ManyToMany
    @JoinTable(name = "item_sizes",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    Set<Size> sizes;

    @OneToMany(mappedBy = "item")
    private Set<ItemInfo> itemInfos;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
