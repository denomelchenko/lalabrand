package com.lalabrand.ecommerce.user.wishlist;

import com.lalabrand.ecommerce.item.ItemDto;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Wishlist}
 */
@Builder
@Value
public class WishlistDto {
    Integer id;
    Set<ItemDto> items;

    public static WishlistDto fromEntity(Wishlist wishlist) {
        return WishlistDto.builder()
                .id(wishlist.getId())
                .items(wishlist.getItems().stream().map(ItemDto::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
