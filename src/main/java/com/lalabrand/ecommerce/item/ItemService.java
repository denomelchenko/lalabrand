package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.utils.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final TranslationService translationService;

    @Autowired
    public ItemService(ItemRepository itemRepository, TranslationService translationService) {
        this.itemRepository = itemRepository;
        this.translationService = translationService;
    }

    public Page<ItemDTO> findBestSellersItems(Optional<Integer> limit) {
        limit = Optional.of(limit.filter(l -> l > 0).orElse(4));
        System.out.println(itemRepository.findItemsByOrderBySoldCountDesc(
                PageRequest.of(0, limit.get())).map(ItemDTO::fromEntity));
    }

    public Page<ItemDTO> findItemsByTitle(String title, Language language, Pageable pageable) {
        if (!language.equals(Language.EN)) {
            title = translationService.textTranslate(language.toString(), Language.EN.toString(), title);
        }
        return itemRepository.findByTitleContainingIgnoreCase(title, pageable).map(ItemDTO::fromEntity);
    }

    public Page<ItemDTO> findItemsByCategoryId(String categoryId, Pageable pageable) {
        return itemRepository.findItemsByCategoryId(categoryId, pageable).map(ItemDTO::fromEntity);
    }

    public Item findItemByIdOrThrow(String itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            logger.error("Item with id: {} does not exist", itemId);
            throw new IllegalArgumentException("Item with this id does not exist");
        }
        return item.get();
    }

    public Set<Item> findItemsByIdsOrThrow(Collection<String> itemsIds) {
        Set<Item> items = itemRepository.findAllByIds(itemsIds);
        if (items.isEmpty() || items.stream().anyMatch(Objects::isNull)) {
            logger.error("One of the items with this Ids does not exist({})", itemsIds.toString());
            throw new IllegalArgumentException("One of the items with this Ids does not exist");
        }
        return items;
    }

    private List<ItemDTO> convertToItemDtoList(List<Item> items) {
        List<ItemDTO> itemDTOList = new LinkedList<>();
        for (Item item : items) {
            itemDTOList.add(ItemDTO.fromEntity(item));
        }
        return itemDTOList;
    }

    public ItemDTO findById(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            throw new IllegalArgumentException("Item wit id: " + id + " does not exist");
        }
        return ItemDTO.fromEntity(item.get());
    }

    public ItemDTO save(ItemInput itemInput) {
        return ItemDTO.fromEntity(itemRepository.save(itemInput.toEntity()));
    }
}
