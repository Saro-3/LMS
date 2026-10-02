package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.Category;
import com.lms.lms_backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category with id " + id + " not found!")
                );
    }

    public Category createCategory(Category category) {

        if(categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException(
                    "Category with name " + category.getName() + " already exists!"
            );
        }

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {

        Category existingCategory = getCategoryById(id);

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {

        Category category = getCategoryById(id);

        categoryRepository.delete(category);
    }
}
