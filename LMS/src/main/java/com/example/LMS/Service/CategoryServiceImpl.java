package com.example.LMS.Service;

import com.example.LMS.dto.CategoryDTO;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return List.of();
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return null;
    }

    @Override
    public void deleteCategoryById(Long id) {

    }

    @Override
    public CategoryDTO updateCategoryById(Long id, CategoryDTO updatedCategoryDTO) {
        return null;
    }
}
