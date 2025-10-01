package com.edu.web.spacecatsmarket.catalog.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDto(
        @NotBlank String name
) {
}
