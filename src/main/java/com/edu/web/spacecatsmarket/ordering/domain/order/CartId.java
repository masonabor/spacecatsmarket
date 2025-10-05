package com.edu.web.spacecatsmarket.ordering.domain.order;

import org.springframework.util.Assert;

import java.util.UUID;

public record CartId(UUID id) {

    public CartId {
        Assert.notNull(id, "id must not be null");
    }

    public static CartId newId() {
        return new CartId(UUID.randomUUID());
    }
}
