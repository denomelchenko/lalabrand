package com.lalabrand.ecommerce.item.category;

import com.lalabrand.ecommerce.utils.CommonUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAllCategory() {
        try {
            List<Category> categories = categoryRepository.findAll();
            if (categories.isEmpty()) {
                throw new EntityNotFoundException("No categories found");
            }
            return categories.stream()
                    .map(CategoryDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            logger.info("No categories found: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.info("Error occurred while fetching categories: " + ex.getMessage());
            throw ex;
        }
    }

    public Optional<CategoryDTO> findCategoryById(String categoryId) {
        if (CommonUtils.isIdInValid(categoryId)) {
            throw new IllegalArgumentException("Id is invalid");
        }
        Optional<Category> categoryEntityOptional = categoryRepository.findById(categoryId);
        if (categoryEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Category not found for id: " + categoryId);
        }
        return categoryEntityOptional.map(CategoryDTO::fromEntity);
    }
}
