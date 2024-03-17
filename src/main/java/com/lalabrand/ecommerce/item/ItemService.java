package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.cart.CartService;
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

    public List<ItemDto> findBestSellersItems(Optional<Integer> limit) {
        limit = Optional.of(limit.filter(l -> l > 0).orElse(4));
        return convertToItemDtoList(itemRepository.findItemsByOrderBySoldCountDesc(
                PageRequest.of(0, limit.get()))
        );
    }

    public List<ItemDto> findItemsByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return Collections.emptyList();
        }
        return convertToItemDtoList(itemRepository.findByTitleContainingIgnoreCase(title));
    }

    public List<ItemDto> findItemsByCategoryId(Integer categoryId) {
        if (categoryId == null) {
            return Collections.emptyList();
        }

        return convertToItemDtoList(itemRepository.findAllByCategoryId(categoryId));
    }

    private List<ItemDto> convertToItemDtoList(List<Item> items) {
        List<ItemDto> itemDtoList = new LinkedList<>();
        for (Item item : items) {
            itemDtoList.add(ItemDto.fromEntity(item));
        }
        return itemDtoList;
    }
}
