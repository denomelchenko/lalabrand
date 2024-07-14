package com.lalabrand.ecommerce.item.material.items_materials;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.material.Material;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "items_materials", schema = "lalabrand")
public class ItemsMaterials {
    @EmbeddedId
    private ItemsMaterialsId id;

    @MapsId("item")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item", nullable = false, insertable = false, updatable = false)
    private Item item;

    @Column(name = "item_id")
    private String itemId;

    @MapsId("materialId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", nullable = false, insertable = false, updatable = false)
    private Material material;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "percentage")
    private Integer percentage;


    public static ItemsMaterials fromItemsMaterialsInput(ItemsMaterialsInput itemsMaterialsInput) {
        return ItemsMaterials.builder()
                .itemId(itemsMaterialsInput.getItemId())
                .materialName(itemsMaterialsInput.getMaterialName())
                .percentage(itemsMaterialsInput.getPercentage())
                .build();
    }
}