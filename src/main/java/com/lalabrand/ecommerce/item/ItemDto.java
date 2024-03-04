package com.lalabrand.ecommerce.item;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Item}
 */
@Value
@Data
@Builder
public class ItemDto implements Serializable {
    Integer id;
    String title;
    String shortDisc;
    String longDisc;
    BigDecimal rating;
    BigDecimal price;
    String currency;
    Integer availableCount;
    Integer salePrice;
    String image;
    Integer soldCount;

    public static ItemDto fromEntity(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .shortDisc(item.getShortDisc())
                .longDisc(item.getLongDisc())
                .rating(item.getRating())
                .price(item.getPrice())
                .currency(item.getCurrency())
                .availableCount(item.getAvailableCount())
                .salePrice(item.getSalePrice())
                .image(item.getImage())
                .soldCount(item.getSoldCount())
                .build();
    }
}