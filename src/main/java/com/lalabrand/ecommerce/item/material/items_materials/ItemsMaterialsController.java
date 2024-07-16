package com.lalabrand.ecommerce.item.material.items_materials;

import com.lalabrand.ecommerce.utils.annotation.Id;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class ItemsMaterialsController {
    private final ItemsMaterialsService itemsMaterialsService;

    public ItemsMaterialsController(ItemsMaterialsService itemsMaterialsService) {
        this.itemsMaterialsService = itemsMaterialsService;
    }

    @MutationMapping("materialToItem")
    @PreAuthorize("hasRole('ADMIN')")
    public ItemsMaterialsDTO addMaterialToItem(@Argument @Valid ItemsMaterialsInput itemsMaterialsInput) {
        return itemsMaterialsService.addMaterialToItem(itemsMaterialsInput);
    }
}
