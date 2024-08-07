package com.lalabrand.ecommerce.order.ordered_item;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.SizeType;
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
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "item_id", nullable = true)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "size", nullable = false, columnDefinition = "ENUM('SHOES','CLOTHES')")
    @Enumerated(EnumType.STRING)
    private SizeType sizeType;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "price", nullable = false, precision = 10)
    private Float price;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "image", nullable = false)
    private String image;
}
