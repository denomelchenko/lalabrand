package com.lalabrand.ecommerce.item;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDto> findBestSellersItems(Optional<Integer> limit) {
        if (limit.isPresent() && limit.get() <= 0) {
            throw new IllegalArgumentException("Limit must be a positive integer");
        } else if (limit.isEmpty()) {
            limit = Optional.of(4);
        }
        return convertToItemDtoList(itemRepository.findItemsByOrderBySoldCountDesc(
                PageRequest.of(0, limit.get(), Sort.by(Sort.Direction.DESC, "soldCount"))
        ));
    }

    public List<ItemDto> findItemsByTitle(String title) {
        return convertToItemDtoList(itemRepository.findByTitleContainingIgnoreCase(title));
    }

    public List<ItemDto> findItemsByCategoryId(Integer categoryId){
        return convertToItemDtoList(itemRepository.findAllByCategoryId(categoryId));
    }

    private List<ItemDto> convertToItemDtoList(List<Item> items) {
        return items.stream()
                .map(ItemDto::fromEntity)
                .toList();
    }
}
