package com.edu.web.spacecatsmarket.dto.product;

import com.edu.web.spacecatsmarket.catalog.domain.Category;
import com.edu.web.spacecatsmarket.validation.CosmicWordCheck;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ResponseProductDto(
        String id,
        String name,
        String description,
        Integer amount,
        Double price,
        Set<String> categories
) {
}
