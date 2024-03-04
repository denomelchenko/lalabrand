package com.lalabrand.ecommerce.item;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDto> getBestSellersItems(Integer limit) {
        if (limit == null || limit <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer");
        }
        return convertToItemDtoList(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "soldCount"))));
    }

    private List<ItemDto> convertToItemDtoList(List<Item> items) {
        return items.stream()
                .map(ItemDto::fromEntity)
                .toList();
    }
}
