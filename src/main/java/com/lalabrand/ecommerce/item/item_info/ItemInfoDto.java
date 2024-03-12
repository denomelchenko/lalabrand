package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.ItemDto;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ItemInfo}
 */
@Value
@Builder
public class ItemInfoDto implements Serializable {
    Integer id;
    ItemDto item;
    ColorEnum color;
    String image;

    public static ItemInfoDto fromEntity(ItemInfo itemInfo) {
        return ItemInfoDto.builder()
                .id(itemInfo.getId())
                .item(ItemDto.fromEntity(itemInfo.getItem()))
                .color(itemInfo.getColor())
                .image(itemInfo.getImage())
                .build();
    }
}