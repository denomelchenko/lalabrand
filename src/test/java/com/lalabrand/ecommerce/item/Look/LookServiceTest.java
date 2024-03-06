package com.lalabrand.ecommerce.item.Look;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.ItemDto;
import com.lalabrand.ecommerce.item.look.Look;
import com.lalabrand.ecommerce.item.look.LookDto;
import com.lalabrand.ecommerce.item.look.LookRepository;
import com.lalabrand.ecommerce.item.look.LookService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LookServiceTest {
    @Mock
    private LookRepository lookRepository;

    @InjectMocks
    private LookService lookService;

    // Should return the first LookDto when no previousLookId is provided
    @Test
    public void test_return_first_LookDto_when_no_previousLookId_provided() {
        LookRepository lookRepository = mock(LookRepository.class);

        lookService = new LookService(lookRepository);

        Look look = new Look();
        look.setId(1);
        look.setImage("image");
        Set<Item> items = new HashSet<>();
        Item item = new Item();
        item.setId(1);
        item.setTitle("item");
        items.add(item);
        look.setItems(items);

        when(lookRepository.findById(1)).thenReturn(Optional.of(look));

        look.setItems(items);
        LookDto expectedLookDto = LookDto.fromEntity(look);

        LookDto result = lookService.getLook(Optional.empty());

        assertEquals(expectedLookDto.getId(), result.getId());
        assertEquals(expectedLookDto.getImage(), result.getImage());
        assertEquals(expectedLookDto.getItems(), result.getItems());
    }

    // Should return an empty LookDto when the database is empty
    @Test
    public void test_return_empty_LookDto_when_database_empty() {
        LookRepository lookRepository = mock(LookRepository.class);

        lookService = new LookService(lookRepository);

        when(lookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            lookService.getLook(Optional.of(1));
        });
    }

}