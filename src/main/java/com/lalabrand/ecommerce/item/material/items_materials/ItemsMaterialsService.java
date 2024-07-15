package com.lalabrand.ecommerce.item.material.items_materials;

import com.lalabrand.ecommerce.item.ItemService;
import com.lalabrand.ecommerce.item.material.MaterialService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemsMaterialsService {
    private final ItemsMaterialsRepository itemsMaterialsRepository;
    private final ItemService itemService;
    private final MaterialService materialService;

    public ItemsMaterialsService(ItemsMaterialsRepository itemsMaterialsRepository, ItemService itemService, MaterialService materialService) {
        this.itemsMaterialsRepository = itemsMaterialsRepository;
        this.itemService = itemService;
        this.materialService = materialService;
    }

    public ItemsMaterialsDTO addMaterialToItem(ItemsMaterialsInput itemsMaterialsInput) {
        if (itemService.findById(itemsMaterialsInput.getItemId()) == null) {
            throw new IllegalArgumentException("Item with id: " + itemsMaterialsInput.getItemId() + " does not exist");
        } else if (materialService.findByName(itemsMaterialsInput.getMaterialName()).isEmpty()) {
            throw new IllegalArgumentException("Material with name: " + itemsMaterialsInput.getMaterialName() + " does not exist");
        } else {
            Optional<ItemsMaterials> itemsMaterials = itemsMaterialsRepository.findByMaterialNameAndItemId(
                    itemsMaterialsInput.getMaterialName(), itemsMaterialsInput.getItemId()
            );
            if (itemsMaterials.isPresent()) {
                itemsMaterials.get().setPercentage(itemsMaterialsInput.getPercentage());
                itemsMaterialsRepository.save(itemsMaterials.get());
            }
        }
        return ItemsMaterialsDTO.fromEntity(
                itemsMaterialsRepository.save(ItemsMaterials.fromItemsMaterialsInput(itemsMaterialsInput))
        );
    }
}
