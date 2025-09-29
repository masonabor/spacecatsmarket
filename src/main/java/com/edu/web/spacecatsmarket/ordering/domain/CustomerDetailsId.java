package com.edu.web.spacecatsmarket.ordering.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record CustomerDetailsId(UUID id) {

    public CustomerDetailsId {
        Assert.notNull(id, "id must not be null");
    }

    public static CustomerDetailsId newId() {
        return new CustomerDetailsId(UUID.randomUUID());
    }
}
