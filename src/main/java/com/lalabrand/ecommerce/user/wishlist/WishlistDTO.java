package com.lalabrand.ecommerce.user.wishlist;

import com.lalabrand.ecommerce.item.ItemDTO;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Wishlist}
 */
@Builder
@Value
public class WishlistDTO {
    String id;
    Set<ItemDTO> items;

    public static WishlistDTO fromEntity(Wishlist wishlist) {
        return WishlistDTO.builder()
                .id(wishlist.getId())
                .items(wishlist.getItems().stream().map(ItemDTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
