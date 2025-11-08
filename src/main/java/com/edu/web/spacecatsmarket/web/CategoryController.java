package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.edu.web.spacecatsmarket.service.CategoryService;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> getAllCategories() {
        List<ResponseCategoryDto> responseCategoryDtos = categoryService.getAll();
        return ResponseEntity.ok(responseCategoryDtos);
    }

    @PostMapping
    public ResponseEntity<ResponseCategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        ResponseCategoryDto response = categoryService.createCategory(createCategoryRequestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCategoryDto> updateCategory(@PathVariable String id,
                                                              @Valid @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
        if (!id.equals(updateCategoryRequestDto.id())) {
            return ResponseEntity.badRequest().build();
        }

        ResponseCategoryDto responseCategory = categoryService.updateCategory(updateCategoryRequestDto);
        return ResponseEntity.ok(responseCategory);
    }

    @DeleteMapping("/{id}")
    @FeatureToggle(FeatureToggles.ADMIN_OPERATION)
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
