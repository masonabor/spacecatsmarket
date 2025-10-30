package com.edu.web.spacecatsmarket.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(

        @NotBlank(message = "Category name must not be empty")
        String name
) {}
