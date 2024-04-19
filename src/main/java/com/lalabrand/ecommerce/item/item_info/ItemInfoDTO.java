package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.enums.ColorEnum;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ItemInfo}
 */
@Value
@Builder
public class ItemInfoDTO implements Serializable {
    String id;
    String image;
    ColorEnum color;

    public static ItemInfoDTO fromEntity(ItemInfo itemInfo) {
        return ItemInfoDTO.builder()
                .id(itemInfo.getId())
                .color(itemInfo.getColor())
                .image(itemInfo.getImage())
                .build();
    }

    public ItemInfo toEntity() {
        return ItemInfo.builder()
                .id(this.id)
                .image(this.image)
                .color(this.color)
                .build();
    }
}
