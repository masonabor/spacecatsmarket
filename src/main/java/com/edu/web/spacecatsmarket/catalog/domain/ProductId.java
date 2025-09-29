package com.edu.web.spacecatsmarket.catalog.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record ProductId(UUID id) {

    public ProductId {
        Assert.notNull(id, "id must not be null");
    }

    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }
}
