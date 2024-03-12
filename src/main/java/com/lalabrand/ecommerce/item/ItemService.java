package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.user.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public List<ItemDto> findBestSellersItems(Optional<Integer> limit) {
        limit = Optional.of(limit.filter(l -> l > 0).orElse(4));
        return convertToItemDtoList(itemRepository.findItemsByOrderBySoldCountDesc(
                PageRequest.of(0, limit.get()))
        );
    }

    public Set<ItemDto> findCartItemsByUserId(Integer userId) {
        return convertToItemDtoSet(cartRepository.findCartByUserId(userId).getItems());
    }

    public List<ItemDto> findItemsByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return Collections.emptyList();
        }
        return convertToItemDtoList(itemRepository.findByTitleContainingIgnoreCase(title));
    }

    private List<ItemDto> convertToItemDtoList(List<Item> items) {
        List<ItemDto> itemDtoList = new LinkedList<>();
        for (Item item : items) {
            itemDtoList.add(ItemDto.fromEntity(item));
        }
        return itemDtoList;
    }

    private Set<ItemDto> convertToItemDtoSet(Set<Item> items) {
        Set<ItemDto> itemDtoSet = new LinkedHashSet<>();
        for (Item item : items) {
            itemDtoSet.add(ItemDto.fromEntity(item));
        }
        return itemDtoSet;
    }
}
