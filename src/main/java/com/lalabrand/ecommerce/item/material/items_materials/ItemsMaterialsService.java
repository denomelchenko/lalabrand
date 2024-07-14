package com.lalabrand.ecommerce.item.material.items_materials;

import org.springframework.stereotype.Service;

@Service
public class ItemsMaterialsService {
    private final ItemsMaterialsRepository itemsMaterialsRepository;

    public ItemsMaterialsService(ItemsMaterialsRepository itemsMaterialsRepository) {
        this.itemsMaterialsRepository = itemsMaterialsRepository;
    }

    public ItemsMaterialsDTO addMaterialToItem(ItemsMaterialsInput itemsMaterialsInput) {
        return ItemsMaterialsDTO.fromEntity(
                itemsMaterialsRepository.save(ItemsMaterials.fromItemsMaterialsInput(itemsMaterialsInput))
        );
    }
}
