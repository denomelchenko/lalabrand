package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.Id;
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

    @MutationMapping(name = "sizeToItemInfo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CommonResponse addSizeToItemInfo(@Argument @Id String itemInfoId, @Argument @Id String sizeId) {
        if (itemInfoService.addSizeToItemInfo(itemInfoId, sizeId)) {
            return CommonResponse.builder()
                    .success(true)
                    .message("Size added to item info successfully")
                    .build();
        } else {
            return CommonResponse.builder()
                    .success(false)
                    .message("Size don`t added to item info")
                    .build();
        }
    }

    @MutationMapping(name = "itemInfo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemInfoDTO createItemInfo(@Argument ItemInfoInput itemInfoInput) {
        return itemInfoService.save(itemInfoInput);
    }
}
