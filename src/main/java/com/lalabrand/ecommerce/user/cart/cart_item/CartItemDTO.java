package com.lalabrand.ecommerce.user.cart.cart_item;

import com.lalabrand.ecommerce.item.ItemDTO;
import com.lalabrand.ecommerce.item.item_info.ItemInfoDTO;
import com.lalabrand.ecommerce.item.size.SizeDTO;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link CartItem}
 */
@Value
@Builder
public class CartItemDTO {
    Integer id;
    ItemDTO item;
    ItemInfoDTO itemInfo;
    SizeDTO size;
    Integer count;

    public static CartItemDTO fromEntity(CartItem cartItem) {
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .item(ItemDTO.fromEntity(cartItem.getItem()))
                .itemInfo(ItemInfoDTO.fromEntity(cartItem.getItemInfo()))
                .size(SizeDTO.fromEntity(cartItem.getSize()))
                .count(cartItem.getCount())
                .build();
    }
}
