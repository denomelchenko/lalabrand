package com.lalabrand.ecommerce.user.cart.cart_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemInput {
    @UUID(message = "ItemId is not valid")
    String itemId;
    @UUID(message = "ItemInfoId is not valid")
    String itemInfoId;
    @UUID(message = "SizeId is not valid")
    String sizeId;
    Integer count;
}
