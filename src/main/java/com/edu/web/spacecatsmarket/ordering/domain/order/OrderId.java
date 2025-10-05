package com.edu.web.spacecatsmarket.ordering.domain.order;

import org.springframework.util.Assert;

import java.util.UUID;

public record OrderId(UUID id) {

    public OrderId {
        Assert.notNull(id, "orderId must not be null");
    }

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID());
    }
}
