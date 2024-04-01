package com.lalabrand.ecommerce.item.category;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void beforeEach() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    // Should return a list of CategoryDTO when findAllCategory is called with valid data
    @Test
    public void test_findAllCategory_validData() {
        // Arrange
        List<Category> categories = new LinkedList<>();
        categories.add(new Category("1", "Category 1", null));
        categories.add(new Category("2", "Category 2", null));
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryDTO> result = categoryService.findAllCategory();

        // Assert
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("2", result.get(1).getId());
        assertEquals("Category 2", result.get(1).getName());
    }

    // Should throw an Exception when findAllCategory is called with no data
    @Test
    public void test_findAllCategory_noData() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Assert
        assertThrows(EntityNotFoundException.class, () -> categoryService.findAllCategory());
    }

    // Should throw an exception when categoryRepository.findAll() throws an Exception
    @Test
    public void test_findAllCategory_exception() {
        // Arrange
        when(categoryRepository.findAll()).thenThrow(new RuntimeException());

        // Assert
        assertThrows(RuntimeException.class, () -> categoryService.findAllCategory());
    }

    // Should throw an EntityNotFoundException when categoryRepository.findAll() return an empty list
    @Test
    public void test_findAllCategory_nullData() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(null);

        // Assert
        assertThrows(RuntimeException.class, () -> categoryService.findAllCategory());
    }

    // Should be able to find a CategoryDTO by id
    @Test
    public void test_findCategoryById() {
        // Arrange
        Category category = new Category("1", "Category 1", null);
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));

        // Act
        Optional<CategoryDTO> result = categoryService.findCategoryById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("Category 1", result.get().getName());
    }

    // Should handle cases where categoryRepository returns duplicate data
    @Test
    public void test_findAllCategory_duplicateData() {
        // Arrange
        List<Category> categories = new LinkedList<>();
        categories.add(new Category("1", "Category 1", null));
        categories.add(new Category("1", "Category 1", null));
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryDTO> result = categoryService.findAllCategory();

        // Assert
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("1", result.get(1).getId());
        assertEquals("Category 1", result.get(1).getName());
    }

}
