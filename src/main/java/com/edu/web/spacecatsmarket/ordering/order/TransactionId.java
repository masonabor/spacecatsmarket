package com.edu.web.spacecatsmarket.ordering.domain.order;

import org.springframework.util.Assert;

import java.util.UUID;

public record TransactionId(UUID id) {

    public TransactionId {
        Assert.notNull(id, "id must not be null");
    }

    public static TransactionId newId() {
        return new TransactionId(UUID.randomUUID());
    }
}
