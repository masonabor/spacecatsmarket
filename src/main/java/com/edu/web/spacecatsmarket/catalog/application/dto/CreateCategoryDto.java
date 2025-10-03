package com.edu.web.spacecatsmarket.catalog.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateCategoryDto(
        @NotBlank String name
) {
}
