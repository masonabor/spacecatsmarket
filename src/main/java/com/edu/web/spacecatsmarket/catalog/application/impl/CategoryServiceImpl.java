package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.CategoryService;
import com.edu.web.spacecatsmarket.catalog.application.dto.CategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.CategoryAlreadyExistException;
import com.edu.web.spacecatsmarket.catalog.application.mapper.CategoryDtoMapper;
import com.edu.web.spacecatsmarket.catalog.domain.Category;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryId;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryName;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryRepository;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
        if (categoryRepository.existsByName(new CategoryName(createCategoryDto.name()))) {
            throw new CategoryAlreadyExistException("Category with name " + createCategoryDto.name() + " already exists");
        }
        Category category = categoryMapper.toDomain(createCategoryDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto getById(UUID id) {
        CategoryId cid = new CategoryId(id);
        return categoryMapper.toDto(categoryRepository.findById(cid)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id)));
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
