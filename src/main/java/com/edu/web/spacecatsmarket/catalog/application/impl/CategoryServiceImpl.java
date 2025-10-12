package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.dto.CategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.CategoryAlreadyExistException;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.catalog.application.mapper.CategoryDtoMapper;
import com.edu.web.spacecatsmarket.catalog.application.service.CategoryService;
import com.edu.web.spacecatsmarket.catalog.domain.Category;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryId;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryName;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryRepository;
import com.edu.web.spacecatsmarket.web.dto.category.UpdateCategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
        categoryRepository.findByName(new CategoryName(createCategoryDto.name())).ifPresent(c -> {
            throw new CategoryAlreadyExistException("Category with name " + createCategoryDto.name() + " already exists");
        });
        Category category = categoryMapper.toDomain(createCategoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto getById(UUID id) {
        CategoryId cid = new CategoryId(id);
        return categoryRepository.findById(cid)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CategoryDto> findById(UUID id) {
        CategoryId cid = new CategoryId(id);
        return categoryRepository.findById(cid)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto updateCategory(UpdateCategoryRequestDto updateDto) {
        CategoryId categoryId = new CategoryId(updateDto.id());
        Category categoryToUpdate = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + updateDto.id()));

        CategoryName newName = new CategoryName(updateDto.name());
        categoryRepository.findByName(newName).ifPresent(existingCategory -> {
            if (!existingCategory.id().equals(categoryId)) {
                throw new CategoryAlreadyExistException("Another category with name " + newName.name() + " already exists");
            }
        });

        categoryToUpdate.name(newName);
        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID id) {
        CategoryId categoryId = new CategoryId(id);
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(categoryId);
    }
}