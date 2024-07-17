package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.enums.ColorEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.hibernate.validator.constraints.UUID;

@Value
@Getter
@Setter
public class ItemInfoInput {
    String image;
    ColorEnum color;
    Boolean isColorAvailable;
    @UUID(message = "Item ID is not valid")
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
