package com.edu.web.spacecatsmarket.catalog.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


public record ProductId(@NotNull(message = "id must not be null") UUID id) {
    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }
}
