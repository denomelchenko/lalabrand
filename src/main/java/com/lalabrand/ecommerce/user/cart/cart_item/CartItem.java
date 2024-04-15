package com.lalabrand.ecommerce.user.cart.cart_item;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.user.cart.Cart;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Item item;

    @Column(name = "item_id")
    private String itemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_info_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ItemInfo itemInfo;

    @Column(name = "item_info_id")
    private String itemInfoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "size_id", insertable = false, updatable = false)
    private Size size;

    @Column(name = "size_id")
    private String sizeId;

    @Column(name = "count")
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_id", nullable = false, updatable = false, insertable = false)
    private Cart cart;

    @Column(name = "cart_id")
    private String cartId;

    public CartItem(String itemId, String itemInfoId, String sizeId, Integer count, String cartId) {
        this.itemId = itemId;
        this.itemInfoId = itemInfoId;
        this.sizeId = sizeId;
        this.count = count;
        this.cartId = cartId;
    }
}
