package com.lalabrand.ecommerce.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
public class ItemInput {
    @NotBlank
    String title;
    @NotBlank
    String shortDisc;
    @NotBlank
    String longDisc;
    Float rating;
    @NotNull
    Float price;
    @NotNull
    Integer availableCount;
    Float salePrice;
    Integer soldCount;
    @NotBlank
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
