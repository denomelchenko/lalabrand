package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.color.Color;
import com.lalabrand.ecommerce.item.item_comment.ItemComment;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.order.ordered_item.OrderedItem;
import com.lalabrand.ecommerce.item.category.Category;
import com.lalabrand.ecommerce.user.cart.Cart;
import com.lalabrand.ecommerce.user.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "short_disc", nullable = false, length = 128)
    private String shortDisc;

    @Column(name = "long_disc")
    private String longDisc;

    @Column(name = "raiting", precision = 10)
    private BigDecimal raiting;

    @Column(name = "price", nullable = false, precision = 10)
    private BigDecimal price;

    @Lob
    @Column(name = "currency", nullable = false)
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "available_count", nullable = false)
    private Integer availableCount;

    @Column(name = "sale_price", nullable = false)
    private Integer salePrice;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "item")
    private Set<ItemComment> itemComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<OrderedItem> orderedItems = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "items")
    private Set<Cart> carts = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "items")
    private Set<Color> colors = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "items")
    private Set<Size> sizes = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "items")
    private Set<Wishlist> wishlists = new LinkedHashSet<>();

}