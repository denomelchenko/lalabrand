package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.item_info.ItemInfoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Item}
 */
@Value
@Getter
@Setter
@Builder
public class ItemDTO implements Serializable {
    String id;
    String title;
    String shortDisc;
    String longDisc;
    Float rating;
    Float price;
    Integer availableCount;
    Float salePrice;
    Integer soldCount;
    Set<ItemInfoDTO> itemInfos;

    public static ItemDTO fromEntity(Item item) {
        if (item.getItemInfos() == null) {
            item.setItemInfos(new HashSet<>());
        }
        return ItemDTO.builder()
                .id(item.getId())
                .title(item.getTitle())
                .shortDisc(item.getShortDisc())
                .longDisc(item.getLongDisc())
                .rating(item.getRating())
                .price(item.getPrice())
                .availableCount(item.getAvailableCount())
                .salePrice(item.getSalePrice())
                .soldCount(item.getSoldCount())
                .itemInfos(item.getItemInfos().stream().map(ItemInfoDTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }

    public Item toEntity() {
        return Item.builder()
                .id(this.id)
                .title(this.title)
                .shortDisc(this.shortDisc)
                .longDisc(this.longDisc)
                .rating(this.rating)
                .price(this.price)
                .availableCount(this.availableCount)
                .salePrice(this.salePrice)
                .soldCount(this.soldCount)
                .build();
    }
}
