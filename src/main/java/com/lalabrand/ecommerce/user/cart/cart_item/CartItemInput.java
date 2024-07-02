package com.lalabrand.ecommerce.user.cart.cart_item;

import com.lalabrand.ecommerce.utils.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemInput {
    @Id(message = "ItemId is not valid")
    String itemId;
    @Id(message = "ItemInfoId is not valid")
    String itemInfoId;
    @Id(message = "SizeId is not valid")
    String sizeId;
    Integer count;
}
