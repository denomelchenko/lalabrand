package com.lalabrand.ecommerce.item.filters;

import com.lalabrand.ecommerce.item.enums.ColorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    String categoryId;
    String sizeId;
    String typeOfPriceSort;
    ColorEnum color;
}
