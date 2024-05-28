package com.lalabrand.ecommerce.item.item_info;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class ItemInfoController {
    private final ItemInfoService itemInfoService;

    public ItemInfoController(ItemInfoService itemInfoService) {
        this.itemInfoService = itemInfoService;
    }

    @MutationMapping(name = "itemInfo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemInfoDTO createItemInfo(@Argument ItemInfoInput itemInfoInput) {
        return itemInfoService.save(itemInfoInput);
    }
}
