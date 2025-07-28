package com.example.LMS.mapper;

import com.example.LMS.dto.CategoryDTO;
import com.example.LMS.entity.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getCatId(), category.getName());
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setCatId(dto.getCatId());
        category.setName(dto.getName());
        return category;
    }
}
