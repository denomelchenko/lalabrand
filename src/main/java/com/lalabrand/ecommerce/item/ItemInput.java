package com.lalabrand.ecommerce.item;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
public class ItemInput {
    String title;
    String shortDisc;
    String longDisc;
    Float rating;
    Float price;
    Integer availableCount;
    Float salePrice;
    Integer soldCount;
    String categoryName;

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
                .categoryName(categoryName)
                .build();
    }
}
