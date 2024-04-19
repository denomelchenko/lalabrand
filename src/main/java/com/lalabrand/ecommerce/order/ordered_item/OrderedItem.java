package com.lalabrand.ecommerce.order.ordered_item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import com.lalabrand.ecommerce.item.size.Size;
import com.lalabrand.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "ordered_item")
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_info_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ItemInfo itemInfo;

    @Column(name = "item_info_id")
    private String itemInfoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "size_id", insertable = false, updatable = false)
    private Size size;

    @Column(name = "size_id")
    private String sizeId;

    @Column(name = "count", nullable = false)
    private Integer count;
}
