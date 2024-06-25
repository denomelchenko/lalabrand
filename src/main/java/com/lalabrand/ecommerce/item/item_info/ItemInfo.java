package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.size.Size;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_info")
public class ItemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @Column(name = "item_id", nullable = false, length = 36)
    private String itemId;

    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToMany
    @JoinTable(
            name = "items_sizes",
            joinColumns = @JoinColumn(name = "item_info_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private Set<Size> sizes = new LinkedHashSet<>();

    @Column(name = "is_color_available", nullable = false)
    private Boolean isColorAvailable = false;

}
