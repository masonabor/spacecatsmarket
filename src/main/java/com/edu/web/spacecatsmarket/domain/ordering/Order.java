package com.edu.web.spacecatsmarket.domain.ordering;

import lombok.Builder;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class Order {

    UUID id;
    UUID transactionId;
    UUID cartId;
    UUID customerId;
    Double price;

    @Builder.Default
    Set<OrderItem> items = new HashSet<>();
}
