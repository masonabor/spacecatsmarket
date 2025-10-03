package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.catalog.application.CategoryService;
import com.edu.web.spacecatsmarket.dto.category.RequestCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.web.mapper.CategoryDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryDtoMapper categoryDtoMapper;

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> getAllCategories() {
        List<ResponseCategoryDto> responseCategoryDtos = categoryService.getAll().stream()
                .map(categoryDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responseCategoryDtos);
    }

    @PostMapping
    public ResponseEntity<ResponseCategoryDto> createCategory(@Valid @RequestBody RequestCategoryDto requestCategoryDto) {
        ResponseCategoryDto response = categoryDtoMapper.toResponse(categoryService.createCategory(
                categoryDtoMapper.toCreate(requestCategoryDto)
        ));
        return ResponseEntity.ok(response);
    }
}
