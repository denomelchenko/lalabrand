package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.item_info.ItemInfoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @NotNull
    @NotBlank
    String title;
    String shortDisc;
    String longDisc;
    BigDecimal rating;
    BigDecimal price;
    Integer availableCount;
    BigDecimal salePrice;
    String image;
    Integer soldCount;
    Set<ItemInfoDTO> itemInfos;

    public static ItemDTO fromEntity(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .title(item.getTitle())
                .shortDisc(item.getShortDisc())
                .longDisc(item.getLongDisc())
                .rating(item.getRating())
                .price(item.getPrice())
                .availableCount(item.getAvailableCount())
                .salePrice(item.getSalePrice())
                .image(item.getImage())
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
                .image(this.image)
                .soldCount(this.soldCount)
                .build();
    }
}
