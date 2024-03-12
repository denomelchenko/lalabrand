package com.lalabrand.ecommerce.item.Category;

import com.lalabrand.ecommerce.item.category.Category;
import com.lalabrand.ecommerce.item.category.CategoryDTO;
import com.lalabrand.ecommerce.item.category.CategoryRepository;
import com.lalabrand.ecommerce.item.category.CategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    private  CategoryRepository categoryRepository;
    private  CategoryService categoryService;

    @BeforeEach
    public void initializationCategoryRepositoryAndCategoryService(){
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }
    @Test
    public void test_getAllCategory(){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", new LinkedHashSet<>()));
        categories.add(new Category(2, "Category 2", new LinkedHashSet<>()));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> result = categoryService.findAllCategory();

        assertEquals(2, result.size());
    }
}