package com.edu.web.spacecatsmarket.dto.product;

import com.edu.web.spacecatsmarket.dto.validation.CosmicWordCheck;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UpdateProductRequestDto(
        @NotBlank(message = "Product id must not be empty")
        String id,

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

        Set<String> categoriesId
) {}