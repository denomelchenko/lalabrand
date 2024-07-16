package com.lalabrand.ecommerce.item;


import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.utils.PaginationRequest;
import com.lalabrand.ecommerce.utils.annotation.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
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

    @QueryMapping(name = "itemsByCategoryId")
    public Page<ItemDTO> findItemsByCategoryId(@Argument @Id String categoryId,
                                               @Argument PaginationRequest paginationRequest) {
        return itemService.findItemsByCategoryId(categoryId,
                paginationRequest.toPageRequest());
    }

    @QueryMapping(name = "itemById")
    public ItemDTO findItemById(@Argument @Id String itemId) {
        return itemService.findById(itemId);
    }

    @QueryMapping(name = "bestSellers")
    public Page<ItemDTO> findBestSellers(@Argument Optional<Integer> limit) {
        return itemService.findBestSellersItems(limit);
    }

    @QueryMapping(name = "itemsByTitle")
    public Page<ItemDTO> findItemsByTitle(@Argument @NotBlank String title,
                                          @Argument Language language,
                                          @Argument PaginationRequest paginationRequest) {
        return itemService.findItemsByTitle(title, language,
                paginationRequest.toPageRequest());
    }

    @MutationMapping(name = "item")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemDTO createItem(@Argument @Valid ItemInput itemInput) {
        return itemService.save(itemInput);
    }
}
