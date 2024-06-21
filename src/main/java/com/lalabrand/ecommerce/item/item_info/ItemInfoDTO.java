package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.size.SizeDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link ItemInfo}
 */
@Value
@Getter
@Setter
@Builder
public class ItemInfoDTO implements Serializable {
    String id;
    String image;
    ColorEnum color;
    Set<SizeDTO> sizes;
    String itemId;
    Boolean isColorAvailable;

    public static ItemInfoDTO fromEntity(ItemInfo itemInfo) {
        return ItemInfoDTO.builder()
                .id(itemInfo.getId())
                .color(itemInfo.getColor())
                .itemId(itemInfo.getItemId())
                .image(itemInfo.getImage())
                .sizes(itemInfo.getSizes().stream().map(SizeDTO::fromEntity).collect(Collectors.toSet()))
                .isColorAvailable(itemInfo.getIsColorAvailable())
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
