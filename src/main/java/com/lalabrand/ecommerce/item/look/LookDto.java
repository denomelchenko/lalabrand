package com.lalabrand.ecommerce.item.look;

import com.lalabrand.ecommerce.item.ItemDto;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Look}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookDto implements Serializable {
    Integer id;
    String image;
    Set<ItemDto> items;

    public static LookDto fromEntity(Look look) {
        return LookDto.builder()
                .id(look.getId())
                .image(look.getImage())
                .items(look.getItems().stream().map(ItemDto::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}