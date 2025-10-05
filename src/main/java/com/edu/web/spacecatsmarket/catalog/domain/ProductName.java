package com.edu.web.spacecatsmarket.catalog.domain;

import com.edu.web.spacecatsmarket.web.validation.CosmicWordCheck;
import jakarta.validation.constraints.NotBlank;

public record ProductName(
        @NotBlank(message = "product name must have text")
        @CosmicWordCheck String name
) {
}
