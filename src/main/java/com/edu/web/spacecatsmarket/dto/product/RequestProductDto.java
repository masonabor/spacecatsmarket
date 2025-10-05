package com.edu.web.spacecatsmarket.dto.product;

import com.edu.web.spacecatsmarket.web.validation.CosmicWordCheck;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Builder
@Jacksonized
public record RequestProductDto(
        @NotBlank @CosmicWordCheck String name,
        @NotBlank String description,
        @NotNull @Min(0) Integer amount,
        @NotNull @Min(0) Double price,
        Set<String> categories
) {}
