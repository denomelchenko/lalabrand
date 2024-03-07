package com.lalabrand.ecommerce.item;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @QueryMapping(name = "bestSellers")
    public List<ItemDto> findBestSellers(@Argument Optional<Integer> limit) {
        return itemService.findBestSellersItems(limit);
    }

    @QueryMapping(name = "itemsByTitle")
    public List<ItemDto> findItemsByTitle(@Argument String title) {
        return itemService.findItemsByTitle(title);
    }
}
