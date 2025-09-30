package com.edu.web.spacecatsmarket.catalog.domain;

import org.springframework.util.Assert;

public record ProductAmount(Integer amount) {

    public ProductAmount {
        Assert.notNull(amount, "Amount must not be null");
        Assert.isTrue(amount >= 0, "Amount must be greater than 0");
    }
}
