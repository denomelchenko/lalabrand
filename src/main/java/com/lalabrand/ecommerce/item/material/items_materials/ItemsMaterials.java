package com.lalabrand.ecommerce.item.material.items_materials;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.material.Material;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items_materials", schema = "lalabrand")
public class ItemsMaterials {
    @EmbeddedId
    private ItemsMaterialsId id;

    @MapsId("item_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @MapsId("material_name")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_name", nullable = false, insertable = false, updatable = false)
    private Material material;

    @Column(name = "percentage")
    private Integer percentage;


    public static ItemsMaterials fromItemsMaterialsInput(ItemsMaterialsInput itemsMaterialsInput) {
        return ItemsMaterials.builder()
                .id(ItemsMaterialsId.builder()
                        .material_name(itemsMaterialsInput.getMaterialName())
                        .item_id(itemsMaterialsInput.getItemId())
                        .build())
                .item(Item.builder().id(itemsMaterialsInput.getItemId()).build())
                .material(Material.builder().name(itemsMaterialsInput.getMaterialName()).build())
                .percentage(itemsMaterialsInput.getPercentage())
                .build();
    }
}