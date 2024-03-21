package com.lalabrand.ecommerce.item.look;

import com.lalabrand.ecommerce.item.Item;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LookServiceTest {
    @Mock
    private LookRepository lookRepository;

    @InjectMocks
    private LookService lookService;

    @BeforeEach
    public void beforeEach() {
        lookRepository = mock(LookRepository.class);

        lookService = new LookService(lookRepository);
    }

    // should return the first LookDto when no previousLookId is provided
    @Test
    public void test_return_first_LookDto_when_no_previousLookId_provided() {
        Look look = new Look();
        look.setId(1);
        look.setImage("image");
        Set<Item> items = new HashSet<>();
        Item item = new Item();
        item.setId(1);
        item.setTitle("item");
        items.add(item);
        look.setItems(items);

        when(lookRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(look));

        look.setItems(items);
        LookDto expectedLookDto = LookDto.fromEntity(look);

        LookDto result = lookService.findLook(Optional.empty());

        assertEquals(expectedLookDto.getId(), result.getId());
        assertEquals(expectedLookDto.getImage(), result.getImage());
        assertEquals(expectedLookDto.getItems(), result.getItems());
    }

    // should return the next LookDto when a previousLookId is provided
    @Test
    public void test_return_next_LookDto_when_previousLookId_provided() {
        Look look = new Look();
        look.setId(1);
        look.setImage("image");
        Set<Item> items = new HashSet<>();
        Item item = new Item();
        item.setId(1);
        item.setTitle("item");
        items.add(item);
        look.setItems(items);

        when(lookRepository.findFirstByIdGreaterThan(1)).thenReturn(Optional.of(look));

        look.setItems(items);
        LookDto expectedLookDto = LookDto.fromEntity(look);

        LookDto result = lookService.findLook(Optional.of(1));

        assertEquals(expectedLookDto.getId(), result.getId());
        assertEquals(expectedLookDto.getImage(), result.getImage());
        assertEquals(expectedLookDto.getItems(), result.getItems());
    }

    // should throw an EntityNotFoundException when the database is empty
    @Test
    public void test_throw_EntityNotFoundException_when_database_empty() {
        System.out.println(lookRepository.findById(1));

        assertThrows(EntityNotFoundException.class, () -> {
            lookService.findLook(Optional.of(1));
        });
    }
}