package com.edu.web.spacecatsmarket.dto.category;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequestDto(

        @NotBlank(message = "Category id must not be empty")
        String id,

        @NotBlank(message = "Category name must not be empty")
        String name
) {}
