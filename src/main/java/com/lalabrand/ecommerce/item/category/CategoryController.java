package com.lalabrand.ecommerce.item.category;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping(name = "allCategories")
    public List<CategoryDTO> findAllCategories() {
        return categoryService.findAllCategory();
    }

    @QueryMapping(name = "categoryById")
    public CategoryDTO findCategoryById(@Argument Integer id) {
        return categoryService.findCategoryById(id).orElse(null);
    }
}

