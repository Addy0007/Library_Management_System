package com.example.LMS.Service;

import com.example.LMS.Repository.CategoryRepository;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.CategoryDTO;
import com.example.LMS.entity.Category;
import com.example.LMS.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getCatId(), category.getName());
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCatId(categoryDTO.getCatId());
        category.setName(categoryDTO.getName());
        return category;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category saved = categoryRepository.save(category);
        return convertToDTO(saved);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getBooksByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(cat -> cat.getBooks().stream()
                        .map(BookMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Override
    public List<BookDTO> getBooksByCategoryName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name);
        if (category == null || category.getBooks() == null) return List.of();
        return category.getBooks().stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategoryById(Long id, CategoryDTO updatedCategoryDTO) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCategoryDTO.getName());
                    Category updated = categoryRepository.save(existing);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }
}
