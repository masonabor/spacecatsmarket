package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.catalog.domain.Category;

import java.util.UUID;

public interface CategoryService {

    Category createCategory(CreateCategoryDto createCategoryDto);
    Category getById(UUID id);
    void deleteCategory(UUID id);
}
