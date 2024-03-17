package com.lalabrand.ecommerce.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach()
    public void before() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    // should return a list of ItemDto when findBestSellersItems is called with a valid limit
    @Test
    public void test_findBestSellersItems_validLimit() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 2))).thenReturn(items);

        List<ItemDto> result = itemService.findBestSellersItems(Optional.of(2));

        assertEquals(2, result.size());
    }

    // Should return an empty list when findItemsByCategoryId is called with a null
    @Test
    public void test_return_empty_list_if_categoryId_is_null() {
        List<ItemDto> result = itemService.findItemsByCategoryId(null);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void test_return_list_of_ItemDto_if_categoryId_is_valid() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId(1);
        items.add(item);
        when(itemRepository.findAllByCategoryId(anyInt())).thenReturn(items);

        List<ItemDto> result = itemService.findItemsByCategoryId(1);

        assertEquals(1, result.get(0).getId());
    }

    @Test
    public void test_handle_and_return_empty_list_if_category_id_is_negative() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        List<ItemDto> result = itemService.findItemsByCategoryId(-1);

        assertEquals(Collections.emptyList(), result);
    }

    // should return an empty list when findItemsByTitle is called with a null or empty title
    @Test
    public void test_findItemsByTitle_nullOrEmptyTitle() {
        List<ItemDto> result = itemService.findItemsByTitle(null);

        assertEquals(Collections.emptyList(), result);

        result = itemService.findItemsByTitle("");

        assertEquals(Collections.emptyList(), result);
    }

    // should return a list of ItemDto when findItemsByTitle is called with a valid title
    @Test
    public void test_findItemsByTitle_validTitle() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findByTitleContainingIgnoreCase("title")).thenReturn(items);

        List<ItemDto> result = itemService.findItemsByTitle("title");

        assertEquals(2, result.size());
    }

    // should return a list of ItemDto with size 4 when findBestSellersItems is called with an empty limit
    @Test
    public void test_findBestSellersItems_emptyLimit() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 4))).thenReturn(items);

        List<ItemDto> result = itemService.findBestSellersItems(Optional.empty());

        assertEquals(4, result.size());
    }

    // should return items when findBestSellersItems is called with a negative limit
    @Test
    public void test_findBestSellersItems_negativeLimit() {
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 5))).thenReturn(new ArrayList<>(4));
        assertEquals(new ArrayList<>(), itemService.findBestSellersItems(Optional.of(-1)));
    }

    // should return an empty list when findItemsByTitle is called with a title that does not exist
    @Test
    public void test_findItemsByTitle_nonExistingTitle() {
        when(itemRepository.findByTitleContainingIgnoreCase("non-existing")).thenReturn(Collections.emptyList());

        List<ItemDto> result = itemService.findItemsByTitle("non-existing");

        assertEquals(Collections.emptyList(), result);
    }

    // should return a list of ItemDto with size equal to the limit when findBestSellersItems is called with a valid limit
    @Test
    public void test_findBestSellersItems_validLimitWithLimit() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 2))).thenReturn(items);

        List<ItemDto> result = itemService.findBestSellersItems(Optional.of(2));

        assertEquals(2, result.size());
    }

    // should return a list of ItemDto with size equal to the number of items found when findItemsByTitle is called with a valid title
    @Test
    public void test_findItemsByTitle_validTitleWithItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findByTitleContainingIgnoreCase("title")).thenReturn(items);

        List<ItemDto> result = itemService.findItemsByTitle("title");

        assertEquals(2, result.size());
    }

    // should return a list of ItemDto with all items sorted by sold count when findBestSellersItems is called with a limit greater than the number of items
    @Test
    public void test_findBestSellersItems_limitGreaterThanItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 3))).thenReturn(items);

        List<ItemDto> result = itemService.findBestSellersItems(Optional.of(3));

        assertEquals(2, result.size());
    }
    @Test
    public void test_findItemsByCategoryId(){
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setId(1);
        items.add(item);
        when(itemRepository.findAllByCategoryId(anyInt())).thenReturn(items);

        List<ItemDto> result = itemService.findItemsByCategoryId(1);

        assertEquals(1, result.get(0).getId());
    }
}