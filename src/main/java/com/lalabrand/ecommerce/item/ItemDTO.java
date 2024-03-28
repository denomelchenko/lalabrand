package com.lalabrand.ecommerce.item;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Item}
 */
@Value
@Getter
@Setter
@Builder
public class ItemDTO implements Serializable {
    Integer id;
    String title;
    String shortDisc;
    String longDisc;
    BigDecimal rating;
    BigDecimal price;
    Integer availableCount;
    BigDecimal salePrice;
    String image;
    Integer soldCount;

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
                .build();
    }
}
