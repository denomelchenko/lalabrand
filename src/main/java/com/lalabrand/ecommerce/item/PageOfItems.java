package com.lalabrand.ecommerce.item;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
@Builder
public class PageOfItems {
    List<ItemDTO> items;
    Long totalCount;
    Integer totalPages;

    public static PageOfItems fromItemsPage(Page<Item> itemsPage) {
        return PageOfItems.builder()
                .items(itemsPage.getContent().stream().map(ItemDTO::fromEntity).toList())
                .totalPages(itemsPage.getTotalPages())
                .totalCount(itemsPage.getTotalElements())
                .build();
    }
}
