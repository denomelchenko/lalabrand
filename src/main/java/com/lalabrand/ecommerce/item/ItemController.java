package com.lalabrand.ecommerce.item;


import com.lalabrand.ecommerce.item.filters.FilterRequest;
import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.utils.PaginationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @QueryMapping(name = "itemsByCategoryName")
    public List<ItemDTO> findItemsByCategoryName(@Argument @NotBlank String categoryName,
                                                 @Argument PaginationRequest paginationRequest) {
        return itemService.findItemsByCategoryName(categoryName,
                paginationRequest.toPageRequest());
    }

    @QueryMapping(name = "itemById")
    public ItemDTO findItemById(@Argument @UUID(message = "Item ID is not valid") String itemId) {
        return itemService.findById(itemId);
    }

    @QueryMapping(name = "bestSellers")
    public List<ItemDTO> findBestSellers(@Argument Optional<Integer> limit) {
        return itemService.findBestSellersItems(limit);
    }

    @QueryMapping(name = "itemsByTitle")
    public List<ItemDTO> findItemsByTitle(@Argument @NotBlank String title,
                                          @Argument Language language,
                                          @Argument PaginationRequest paginationRequest) {
        return itemService.findItemsByTitle(title, language,
                paginationRequest.toPageRequest());
    }

    @MutationMapping(name = "item")
    @PreAuthorize("hasAuthority('USER')")
    public ItemDTO createItem(@Argument @Valid ItemInput itemInput) {
        return itemService.save(itemInput);
    }

    @QueryMapping(name = "itemsFilter")
    public List<ItemDTO> itemsFilter(@Argument FilterRequest filterRequest) {
        return itemService.filterItems(filterRequest);
    }
}
