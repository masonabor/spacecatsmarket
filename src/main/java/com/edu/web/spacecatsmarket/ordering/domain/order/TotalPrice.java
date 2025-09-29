package com.edu.web.spacecatsmarket.ordering.domain.order;

import org.springframework.util.Assert;

public record TotalPrice(Double totalPrice) {

    public TotalPrice {
        Assert.notNull(totalPrice, "total price must not be null");
        Assert.isTrue(totalPrice > 0, "total price must be greater than 0");
    }
}
