package com.lalabrand.ecommerce.item.look;

import com.lalabrand.ecommerce.item.ItemDTO;
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
public class LookDTO implements Serializable {
    Integer id;
    String image;
    Set<ItemDTO> items;

    public static LookDTO fromEntity(Look look) {
        return LookDTO.builder()
                .id(look.getId())
                .image(look.getImage())
                .items(look.getItems().stream().map(ItemDTO::fromEntity).collect(Collectors.toSet()))
                .build();
    }
}
