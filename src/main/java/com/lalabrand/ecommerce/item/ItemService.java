package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.filters.FilterRequest;
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
import java.util.stream.Collectors;

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

    public PageOfItems findBestSellersItems(Optional<Integer> limit) {
        limit = Optional.of(limit.filter(l -> l > 0).orElse(4));
        return PageOfItems.fromItemsPage(itemRepository.findItemsByOrderBySoldCountDesc(
                PageRequest.of(0, limit.get())));
    }

    public PageOfItems findItemsByTitle(String title, Language language, Pageable pageable) {
        if (!language.equals(Language.EN)) {
            title = translationService.textTranslate(language.toString(), Language.EN.toString(), title);
        }
        return PageOfItems.fromItemsPage(itemRepository.findByTitleContainingIgnoreCase(title, pageable));
    }

    public PageOfItems findItemsByCategoryName(String categoryName, Pageable pageable) {
        return PageOfItems.fromItemsPage(itemRepository.findItemsByCategoryNameIgnoreCase(categoryName, pageable));
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

    public List<ItemDTO> filterItems(FilterRequest request) {
        logger.info("Filter parameters - CategoryName: {}, Color: {}, SizeId: {}", request.getCategoryName(), request.getColor(), request.getSizeId());

        List<Item> filteredList = itemRepository.findFilteredItems(request.getCategoryName(), request.getColor(), request.getSizeId());
        if (filteredList.isEmpty()) {
            logger.warn("No items found with the given filter parameters.");
        } else {
            logger.info("Found {} items with the given filter parameters.", filteredList.size());
        }

        if ("asc".equalsIgnoreCase(request.getTypeOfPriceSort())) {
            return ItemDTO.fromEntityList(filteredList.stream()
                    .sorted(Comparator.comparing(Item::getPrice))
                    .collect(Collectors.toList()));
        } else if ("desc".equalsIgnoreCase(request.getTypeOfPriceSort())) {
            return ItemDTO.fromEntityList(filteredList.stream()
                    .sorted(Comparator.comparing(Item::getPrice).reversed())
                    .collect(Collectors.toList()));
        }
        return ItemDTO.fromEntityList(filteredList);
    }
}
