package com.edu.web.spacecatsmarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProductDto(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull @Min(0) Double price
) {
}