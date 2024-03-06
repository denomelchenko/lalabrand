package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.category.Category;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ItemController {
    private final ItemRepository itemRepository;
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @QueryMapping
    public List<Item> getItemsByCategoryId(@Argument Integer id){
        return itemRepository.findAllByCategoryId(id);
    }
}
