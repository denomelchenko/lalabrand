package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.size.SizeDTO;
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
    SizeDTO size;
    Boolean isColorAvailable;

    public static ItemInfoDTO fromEntity(ItemInfo itemInfo) {
        return ItemInfoDTO.builder()
                .id(itemInfo.getId())
                .color(itemInfo.getColor())
                .image(itemInfo.getImage())
                .isColorAvailable(itemInfo.getIsColorAvailable())
                .build();
    }
}
