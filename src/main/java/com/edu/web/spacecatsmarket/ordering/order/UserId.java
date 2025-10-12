package com.edu.web.spacecatsmarket.ordering.domain.order;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID id) {

    public UserId {
        Assert.notNull(id, "id must not be null");
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
}
