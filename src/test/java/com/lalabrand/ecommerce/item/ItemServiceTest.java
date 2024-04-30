package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.user.enums.Language;
import com.lalabrand.ecommerce.utils.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach()
    public void setUp() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository, mock(TranslationService.class));
    }

    // should return a list of best-selling items when findBestSellersItems is called
    @Test
    public void test_findBestSellersItems() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId("1");
        items.add(item);
        when(itemRepository.findItemsByOrderBySoldCountDesc(any(Pageable.class))).thenReturn(items);

        List<ItemDTO> result = itemService.findBestSellersItems(Optional.of(4));

        assertEquals("1", result.get(0).getId());
    }

    // should return a list of items when findItemsByTitle is called with a non-empty title
    @Test
    public void test_findItemsByTitle() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId("1");
        items.add(item);
        when(itemRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(items);

        List<ItemDTO> result = itemService.findItemsByTitle("title", Language.EN);

        assertEquals("1", result.get(0).getId());
    }

    // should return a list of items when findItemsByCategoryId is called with a valid category id
    @Test
    public void test_findItemsByCategoryId() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId("1");
        items.add(item);
        when(itemRepository.findItemsByCategoryId(anyString())).thenReturn(items);

        List<ItemDTO> result = itemService.findItemsByCategoryId("1");

        assertEquals("1", result.get(0).getId());
    }

    // should return an item when findItemByIdOrThrow is called with a valid item id
    @Test
    public void test_findItemByIdOrThrow() {
        Item item = new Item();
        item.setId("1");
        when(itemRepository.findById(anyString())).thenReturn(Optional.of(item));

        Item result = itemService.findItemByIdOrThrow("1");

        assertEquals("1", result.getId());
    }

    // should return a set of items when findItemsByIdsOrThrow is called with a valid collection of item ids
    @Test
    public void test_findItemsByIdsOrThrow() {
        Set<Item> items = new HashSet<>();
        Item item = new Item();
        item.setId("1");
        items.add(item);
        when(itemRepository.findAllByIds(anyCollection())).thenReturn(items);

        Set<Item> result = itemService.findItemsByIdsOrThrow(Collections.singleton("1"));

        assertEquals("1", result.iterator().next().getId());
    }

    // should throw an IllegalArgumentException when findItemsByTitle is called with an empty title
    @Test
    public void test_findItemsByTitle_emptyTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            itemService.findItemsByTitle("", Language.EN);
        });
    }

    // should throw an IllegalArgumentException when findItemByIdOrThrow is called with an invalid item id
    @Test
    public void test_findItemByIdOrThrow_invalidItemId() {
        when(itemRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            itemService.findItemByIdOrThrow("invalid");
        });
    }

    // should throw an IllegalArgumentException when findItemsByIdsOrThrow is called with an invalid collection of item ids
    @Test
    public void test_findItemsByIdsOrThrow_invalidItemIds() {
        Set<Item> items = new HashSet<>();
        items.add(null);
        when(itemRepository.findAllByIds(anyCollection())).thenReturn(items);

        assertThrows(IllegalArgumentException.class, () -> {
            itemService.findItemsByIdsOrThrow(Collections.singleton("1"));
        });
    }

    // should handle gracefully when limit is not present in findBestSellersItems
    @Test
    public void test_findBestSellersItems_noLimit() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId("1");
        items.add(item);
        when(itemRepository.findItemsByOrderBySoldCountDesc(any(Pageable.class))).thenReturn(items);

        List<ItemDTO> result = itemService.findBestSellersItems(Optional.empty());

        assertEquals("1", result.get(0).getId());
    }

    // should handle gracefully when limit is less than or equal to zero in findBestSellersItems
    @Test
    public void test_findBestSellersItems_zeroLimit() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId("1");
        items.add(item);
        when(itemRepository.findItemsByOrderBySoldCountDesc(any(Pageable.class))).thenReturn(items);

        List<ItemDTO> result = itemService.findBestSellersItems(Optional.of(0));

        assertEquals("1", result.get(0).getId());
    }

    // should handle gracefully when itemRepository returns an empty list in convertToItemDtoList
    @Test
    public void test_convertToItemDtoList_emptyList() {
        List<Item> items = new ArrayList<>();
        when(itemRepository.findItemsByCategoryId(anyString())).thenReturn(items);

        List<ItemDTO> result = itemService.findItemsByCategoryId("1");

        assertTrue(result.isEmpty());
    }

}
