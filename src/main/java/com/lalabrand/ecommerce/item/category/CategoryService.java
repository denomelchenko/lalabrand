package com.lalabrand.ecommerce.item.category;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<CategoryDTO> findAllCategory(){
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDTO(category.getId(),category.getName()))
                .toList();
    }
}