package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    ResponseCategoryDto createCategory(CreateCategoryRequestDto createCategoryRequestDto);
    ResponseCategoryDto updateCategory(UpdateCategoryRequestDto updateCategoryRequestDto);
    ResponseCategoryDto getById(UUID id);
    List<ResponseCategoryDto> getAll();
    void delete(UUID id);
}
