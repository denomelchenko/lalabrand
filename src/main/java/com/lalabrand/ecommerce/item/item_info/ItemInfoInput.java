package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.size.SizeInput;

public class ItemInfoInput {
    String image;
    ColorEnum color;
    Boolean isColorAvailable;

    public ItemInfo toEntity() {
        return ItemInfo.builder()
                .color(this.color)
                .image(this.image)
                .isColorAvailable(this.isColorAvailable)
                .build();
    }
}
