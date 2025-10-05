package com.edu.web.spacecatsmarket.dto.product;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Builder
@Jacksonized
public record ResponseProductDto(
        String id,
        String name,
        String description,
        Integer amount,
        Double price,
        Set<String> categories
) {
}
