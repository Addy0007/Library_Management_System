package com.example.LMS.Service;

import com.example.LMS.entity.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    void deleteCategoryById(Long id);
    Category updateCategoryById(Long id,Category updatedCategory);
}

