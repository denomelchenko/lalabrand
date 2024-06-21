package com.lalabrand.ecommerce.item.item_info;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class ItemInfoController {
    private final ItemInfoService itemInfoService;

    public ItemInfoController(ItemInfoService itemInfoService) {
        this.itemInfoService = itemInfoService;
    }

    @MutationMapping(name = "sizeToItemInfo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemInfoDTO addSizeToItemInfo(@Argument String itemInfoId, @Argument String sizeId) {
        return itemInfoService.addSizeToItemInfo(itemInfoId, sizeId);
    }

    @MutationMapping(name = "sizesToItemInfo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemInfoDTO addSizesToItemInfo(@Argument String itemInfoId, @Argument Set<String> sizeIds) {
        return itemInfoService.addSizesToItemInfo(itemInfoId, sizeIds);
    }

    @MutationMapping(name = "itemInfo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemInfoDTO createItemInfo(@Argument ItemInfoInput itemInfoInput) {
        return itemInfoService.save(itemInfoInput);
    }
}
