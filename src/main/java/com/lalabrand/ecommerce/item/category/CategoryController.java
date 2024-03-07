package com.lalabrand.ecommerce.item.category;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
@Controller
public class CategoryController {
    private CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @QueryMapping(name = "allCategories")
    public List<Category> allCategories(){
        return categoryRepository.findAll();
    }
}
