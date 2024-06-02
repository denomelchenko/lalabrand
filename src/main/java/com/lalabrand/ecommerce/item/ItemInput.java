package com.lalabrand.ecommerce.item;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;

@Getter
@Setter
@Value
public class ItemInput {
    String title;
    String shortDisc;
    String longDisc;
    BigDecimal rating;
    BigDecimal price;
    Integer availableCount;
    BigDecimal salePrice;
    Integer soldCount;
    String categoryId;

    public Item toEntity() {
        return Item.builder()
                .title(this.title)
                .shortDisc(this.shortDisc)
                .longDisc(this.longDisc)
                .rating(this.rating)
                .price(this.price)
                .availableCount(this.availableCount)
                .salePrice(this.salePrice)
                .soldCount(this.soldCount)
                .categoryId(categoryId)
                .build();
    }
}
