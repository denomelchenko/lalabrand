package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.ItemDTO;
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
    Integer id;
    ItemDTO item;
    String image;
    ColorEnum color;

    public static ItemInfoDTO fromEntity(ItemInfo itemInfo) {
        return ItemInfoDTO.builder()
                .id(itemInfo.getId())
                .color(itemInfo.getColor())
                .item(ItemDTO.fromEntity(itemInfo.getItem()))
                .image(itemInfo.getImage())
                .build();
    }
}
