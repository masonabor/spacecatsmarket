package com.edu.web.spacecatsmarket.dto.product;


import com.edu.web.spacecatsmarket.validation.CosmicWordCheck;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateProductDto(
        @NotBlank @CosmicWordCheck String name,
        @NotBlank String description,
        @NotNull @Min(0) Integer amount,
        @NotNull @Min(0) Double price,
        Set<String> categories
    ) {}

