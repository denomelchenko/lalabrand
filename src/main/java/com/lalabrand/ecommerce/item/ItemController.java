package com.lalabrand.ecommerce.item;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
