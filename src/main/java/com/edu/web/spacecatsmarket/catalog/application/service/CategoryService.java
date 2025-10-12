package com.edu.web.spacecatsmarket.catalog.application.service;

import com.edu.web.spacecatsmarket.catalog.application.dto.CategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.web.dto.category.UpdateCategoryRequestDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
public interface CategoryService {

    CategoryDto createCategory(@Valid CreateCategoryDto createCategoryDto);
    CategoryDto getById(UUID id);
    List<CategoryDto> getAll();
    Optional<CategoryDto> findById(UUID id);
    CategoryDto updateCategory(UpdateCategoryRequestDto updateDto);
    void deleteCategory(UUID id);
}
