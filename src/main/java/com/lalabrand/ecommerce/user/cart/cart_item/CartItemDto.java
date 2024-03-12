package com.lalabrand.ecommerce.user.cart.cart_item;

import com.lalabrand.ecommerce.item.ItemDto;
import com.lalabrand.ecommerce.item.item_info.ItemInfoDto;
import com.lalabrand.ecommerce.item.size.SizeDto;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link CartItem}
 */
@Value
@Builder
public class CartItemDto {
    Integer id;
    ItemDto item;
    ItemInfoDto itemInfo;
    SizeDto size;
    Integer count;

    public static CartItemDto fromEntity(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .item(ItemDto.fromEntity(cartItem.getItem()))
                .itemInfo(ItemInfoDto.fromEntity(cartItem.getItemInfo()))
                .size(SizeDto.fromEntity(cartItem.getSize()))
                .count(cartItem.getCount())
                .build();
    }
}
