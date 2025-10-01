package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.CategoryService;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.mapper.CategoryDtoMapper;
import com.edu.web.spacecatsmarket.catalog.domain.Category;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryId;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryRepository;
import com.edu.web.spacecatsmarket.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryMapper;

    @Override
    public Category createCategory(CreateCategoryDto createCategoryDto) {
        Category category = categoryMapper.toDomain(createCategoryDto);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category getById(UUID id) {
        CategoryId cid = new CategoryId(id);
        return categoryRepository.findById(cid)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));
    }

    @Override
    public void deleteCategory(UUID id) {
        CategoryId cid = new CategoryId(id);
        categoryRepository.delete(cid);
    }
}
