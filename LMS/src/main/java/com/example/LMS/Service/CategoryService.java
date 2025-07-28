package com.example.LMS.Service;

import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.CategoryDTO;


import java.util.List;

public interface CategoryService {
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    void deleteCategoryById(Long id);

    List<BookDTO> getBooksByCategoryId(Long categoryId);
    List<BookDTO> getBooksByCategoryName(String name);

    CategoryDTO updateCategoryById(Long id, CategoryDTO updatedCategoryDTO);
}

