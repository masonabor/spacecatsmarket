package com.edu.web.spacecatsmarket.catalog.domain;

import org.springframework.util.Assert;

public record ProductName(String name) {

    public ProductName {
        Assert.notNull(name, "product name must nit be null");
        Assert.hasText(name, "product name must have text");
    }
}
