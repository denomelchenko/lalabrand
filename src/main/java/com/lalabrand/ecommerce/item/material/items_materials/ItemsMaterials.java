package com.lalabrand.ecommerce.item.material;

import com.lalabrand.ecommerce.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "items_materials", schema = "lalabrand")
public class ItemsMaterial {
    @EmbeddedId
    private ItemsMaterialId id;

    @MapsId("item")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item", nullable = false)
    private Item item;

    @MapsId("materialId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name = "percentage")
    private Integer percentage;

}