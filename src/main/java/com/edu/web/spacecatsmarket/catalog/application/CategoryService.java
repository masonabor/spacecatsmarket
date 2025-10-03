package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.catalog.application.dto.CategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface CategoryService {

    CategoryDto createCategory(@Valid CreateCategoryDto createCategoryDto);
    CategoryDto getById(UUID id);
    List<CategoryDto> getAll();
}
