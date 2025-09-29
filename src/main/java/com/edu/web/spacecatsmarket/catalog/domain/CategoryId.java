package com.edu.web.spacecatsmarket.catalog.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record CategoryId(UUID id) {

    public CategoryId {
        Assert.notNull(id, "id must not be null");
    }

    public static CategoryId newId() {
        return new CategoryId(UUID.randomUUID());
    }
}
