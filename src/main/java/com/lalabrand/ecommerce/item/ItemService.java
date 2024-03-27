package com.lalabrand.ecommerce.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> findBestSellersItems(Optional<Integer> limit) {
        limit = Optional.of(limit.filter(l -> l > 0).orElse(4));
        return convertToItemDtoList(itemRepository.findItemsByOrderBySoldCountDesc(
                PageRequest.of(0, limit.get()))
        );
    }

    public List<ItemDTO> findItemsByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return Collections.emptyList();
        }
        return convertToItemDtoList(itemRepository.findByTitleContainingIgnoreCase(title));
    }

    private List<ItemDTO> convertToItemDtoList(List<Item> items) {
        List<ItemDTO> itemDTOList = new LinkedList<>();
        for (Item item : items) {
            itemDTOList.add(ItemDTO.fromEntity(item));
        }
        return itemDTOList;
    }
}
