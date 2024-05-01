package com.lalabrand.ecommerce.item;


import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.utils.Id;
import jakarta.validation.constraints.NotBlank;
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

    @QueryMapping(name = "itemsByCategoryId")
    public List<ItemDTO> findItemsByCategoryId(@Argument @Id String categoryId) {
        return itemService.findItemsByCategoryId(categoryId);
    }

    @QueryMapping(name = "itemById")
    public ItemDTO findItemById(@Argument @Id String itemId) {
        return itemService.findById(itemId);
    }

    @QueryMapping(name = "bestSellers")
    public List<ItemDTO> findBestSellers(@Argument Optional<Integer> limit) {
        return itemService.findBestSellersItems(limit);
    }

    @QueryMapping(name = "itemsByTitle")
    public List<ItemDTO> findItemsByTitle(@Argument @NotBlank String title, @Argument Language language) {
        return itemService.findItemsByTitle(title, language);
    }
}
