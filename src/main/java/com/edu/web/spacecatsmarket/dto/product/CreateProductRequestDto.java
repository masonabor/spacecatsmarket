package com.edu.web.spacecatsmarket.dto.product;

import com.edu.web.spacecatsmarket.dto.validation.CosmicWordCheck;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateProductRequestDto(
        @NotBlank(message = "Product name must not be blank")
        @CosmicWordCheck(message = "Product name contains forbidden 'cosmic' words")
        String name,

        @NotBlank(message = "Description must not be blank")
        String description,

        @NotNull(message = "Amount must not be null")
        @Min(value = 0, message = "Amount must be zero or greater")
        Integer amount,

        @NotNull(message = "Price must not be null")
        @Min(value = 0, message = "Price must be zero or greater")
        Double price,

        @NotEmpty(message = "Product must have at least one category")
        Set<String> categoriesId
) {}

