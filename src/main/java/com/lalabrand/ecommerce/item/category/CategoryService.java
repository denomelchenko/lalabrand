package com.lalabrand.ecommerce.item.category;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAllCategory() {
        try {
            return categoryRepository.findAll().stream().map(CategoryDTO::fromEntity).toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Optional<CategoryDTO> findCategoryById(Integer id) {
        try {
            if (id == null || id < 1) {
                throw new IllegalArgumentException("Id can not be null or less than 1");
            }
            return categoryRepository.findById(id).map(CategoryDTO::fromEntity);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Optional.empty();
        }
    }
}