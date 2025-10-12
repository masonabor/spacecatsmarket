package com.edu.web.spacecatsmarket.web.controller.category;

import com.edu.web.spacecatsmarket.catalog.application.service.CategoryService;
import com.edu.web.spacecatsmarket.web.dto.category.RequestCategoryDto;
import com.edu.web.spacecatsmarket.web.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.web.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.web.mapper.CategoryDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryDtoMapper categoryDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseCategoryDto> createCategory(@Valid @RequestBody RequestCategoryDto requestCategoryDto) {
        ResponseCategoryDto createdCategory = categoryDtoMapper.toResponse(
                categoryService.createCategory(categoryDtoMapper.toCreate(requestCategoryDto))
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.id())
                .toUri();

        return ResponseEntity.created(location).body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> getAllCategories() {
        List<ResponseCategoryDto> responseCategoryDtos = categoryService.getAll().stream()
                .map(categoryDtoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responseCategoryDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCategoryDto> getCategoryById(@PathVariable UUID id) {
        return categoryService.findById(id)
                .map(categoryDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCategoryDto> updateCategory(@PathVariable UUID id,
                                                              @Valid @RequestBody RequestCategoryDto request) {
        UpdateCategoryRequestDto updateDto = categoryDtoMapper.toUpdate(id, request);
        ResponseCategoryDto updatedCategory = categoryDtoMapper.toResponse(
                categoryService.updateCategory(updateDto)
        );
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}