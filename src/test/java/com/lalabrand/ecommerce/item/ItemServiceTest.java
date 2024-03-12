package com.lalabrand.ecommerce.item;

import com.lalabrand.ecommerce.item.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ItemServiceTest {


    // Should return a list of ItemDto objects when getBestSellersItems is called with a valid limit parameter
    @Test
    public void test_getBestSellersItems_validLimit() {
        // Arrange
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());
        when(itemRepository.findItemsByOrderBySoldCountDesc(PageRequest.of(0, 2))).thenReturn(items);

        List<ItemDto> result = itemService.findBestSellersItems(Optional.of(2));

        assertEquals(2, result.size());
    }

    // Should throw an IllegalArgumentException when getBestSellersItems is called with a negative limit parameter
    @Test
    public void test_getBestSellersItems_negativeLimit() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        ItemService itemService = new ItemService(itemRepository);

        assertThrows(IllegalArgumentException.class, () -> itemService.findBestSellersItems(Optional.of(-1)));
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