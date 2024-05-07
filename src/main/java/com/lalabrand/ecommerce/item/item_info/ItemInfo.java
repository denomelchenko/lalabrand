package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item_info")
public class ItemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @Column(name = "item_id", nullable = false)
    private String item_id;

    @Column(name = "color", nullable = false, columnDefinition = "ENUM('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE')")
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "is_color_available", nullable = false)
    private Boolean isColorAvailable = false;

}
