package com.edu.web.spacecatsmarket.ordering.domain;

import org.springframework.util.Assert;

public record CustomerName(String name) {

    public CustomerName {
        Assert.notNull(name, "customer name must not be null");
        Assert.hasText(name, "customer name must have text");
    }
}
