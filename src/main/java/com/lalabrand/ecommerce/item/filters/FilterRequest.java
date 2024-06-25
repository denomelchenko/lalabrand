package com.lalabrand.ecommerce.item.filters;

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
    String color;
}
