package com.edu.web.spacecatsmarket.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record RequestCategoryDto(
        @NotBlank String name
) {}
