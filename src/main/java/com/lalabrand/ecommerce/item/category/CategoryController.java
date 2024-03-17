package com.lalabrand.ecommerce.item.category;

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
    public List<CategoryDTO> allCategories(){
        return categoryService.findAllCategory();
    }
}
