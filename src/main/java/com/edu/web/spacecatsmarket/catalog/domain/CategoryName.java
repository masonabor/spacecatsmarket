package com.edu.web.spacecatsmarket.catalog.domain;

import org.springframework.util.Assert;

public record CategoryName(String name) {

    public CategoryName {
        Assert.notNull(name, "category name must not be null");
        Assert.hasText(name, "category name must have text");
    }
}
