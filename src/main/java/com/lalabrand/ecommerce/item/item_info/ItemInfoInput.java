package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.size.SizeInput;
import com.lalabrand.ecommerce.utils.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Getter
@Setter
public class ItemInfoInput {
    String image;
    ColorEnum color;
    Boolean isColorAvailable;
    @Id
    String itemId;

    public ItemInfo toEntity() {
        return ItemInfo.builder()
                .color(this.color)
                .image(this.image)
                .isColorAvailable(this.isColorAvailable)
                .itemId(this.itemId)
                .build();
    }
}
