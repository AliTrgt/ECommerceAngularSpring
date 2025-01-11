package com.example.E_commerce.service;


import com.example.E_commerce.dto.CategoryDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.mapper.CategoryMapper;
import com.example.E_commerce.model.Category;
import com.example.E_commerce.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    public CategoryDto findById(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IdNotFoundException(categoryId));
        return categoryMapper.toDto(category);
    }

    public CategoryDto createCategory(Category category) {
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    public CategoryDto updateCategoryName(int categoryId, Category category) {
        Category currentCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new IdNotFoundException(categoryId));

        currentCategory.setName(category.getName());

        categoryRepository.save(currentCategory);
        return categoryMapper.toDto(currentCategory);
    }

    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IdNotFoundException(categoryId));
        if (category != null) {
            categoryRepository.deleteById(categoryId);
            log.info("Successfully Deleted");
        } else {
            log.info("Id Not Found !!");
        }
    }
}
