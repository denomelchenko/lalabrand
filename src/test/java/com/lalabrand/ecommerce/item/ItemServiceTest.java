package com.lalabrand.ecommerce.item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Before()
    public void before() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    // should return a list of ItemDto when findBestSellersItems is called with a valid limit
    @Test
    public void test_findBestSellersItems_validLimit() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 2))).thenReturn(items);

        // Act
        List<ItemDto> result = itemService.findBestSellersItems(Optional.of(2));

        // Assert
        assertEquals(2, result.size());
    }

    // should return an empty list when findItemsByTitle is called with a null or empty title
    @Test
    public void test_findItemsByTitle_nullOrEmptyTitle() {
        // Arrange

        // Act
        List<ItemDto> result = itemService.findItemsByTitle(null);

        // Assert
        assertEquals(Collections.emptyList(), result);

        // Act
        result = itemService.findItemsByTitle("");

        // Assert
        assertEquals(Collections.emptyList(), result);
    }

    // should return a list of ItemDto when findItemsByTitle is called with a valid title
    @Test
    public void test_findItemsByTitle_validTitle() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findByTitleContainingIgnoreCase("title")).thenReturn(items);

        // Act
        List<ItemDto> result = itemService.findItemsByTitle("title");

        // Assert
        assertEquals(2, result.size());
    }

    // should return a list of ItemDto with size 4 when findBestSellersItems is called with an empty limit
    @Test
    public void test_findBestSellersItems_emptyLimit() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 4))).thenReturn(items);

        // Act
        List<ItemDto> result = itemService.findBestSellersItems(Optional.empty());

        // Assert
        assertEquals(4, result.size());
    }

    // should return items when findBestSellersItems is called with a negative limit
    @Test
    public void test_findBestSellersItems_negativeLimit() {
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 5))).thenReturn(new ArrayList<>(4));
        // Act and Assert
        assertEquals(new ArrayList<>(), itemService.findBestSellersItems(Optional.of(-1)));
    }

    // should return an empty list when findItemsByTitle is called with a title that does not exist
    @Test
    public void test_findItemsByTitle_nonExistingTitle() {
        // Arrange
        when(itemRepository.findByTitleContainingIgnoreCase("non-existing")).thenReturn(Collections.emptyList());

        // Act
        List<ItemDto> result = itemService.findItemsByTitle("non-existing");

        // Assert
        assertEquals(Collections.emptyList(), result);
    }

    // should return a list of ItemDto with size equal to the limit when findBestSellersItems is called with a valid limit
    @Test
    public void test_findBestSellersItems_validLimitWithLimit() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 2))).thenReturn(items);

        // Act
        List<ItemDto> result = itemService.findBestSellersItems(Optional.of(2));

        // Assert
        assertEquals(2, result.size());
    }

    // should return a list of ItemDto with size equal to the number of items found when findItemsByTitle is called with a valid title
    @Test
    public void test_findItemsByTitle_validTitleWithItems() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findByTitleContainingIgnoreCase("title")).thenReturn(items);

        // Act
        List<ItemDto> result = itemService.findItemsByTitle("title");

        // Assert
        assertEquals(2, result.size());
    }

    // should return a list of ItemDto with all items sorted by sold count when findBestSellersItems is called with a limit greater than the number of items
    @Test
    public void test_findBestSellersItems_limitGreaterThanItems() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 3))).thenReturn(items);

        // Act
        List<ItemDto> result = itemService.findBestSellersItems(Optional.of(3));

        // Assert
        assertEquals(2, result.size());
    }

}