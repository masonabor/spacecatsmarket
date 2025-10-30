package com.edu.web.spacecatsmarket.dto.category;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ResponseCategoryDto(
        String id,
        String name
) {}
