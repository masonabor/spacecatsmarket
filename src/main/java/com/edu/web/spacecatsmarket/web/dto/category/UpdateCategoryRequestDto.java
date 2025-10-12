package com.edu.web.spacecatsmarket.web.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequestDto(
   @NotNull String id,
   @NotNull @NotBlank String name
) {}
