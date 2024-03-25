package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_info")
public class ItemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @Column(name = "image")
    private String image;

}
