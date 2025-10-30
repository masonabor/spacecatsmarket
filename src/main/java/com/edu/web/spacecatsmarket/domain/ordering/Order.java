package com.edu.web.spacecatsmarket.domain.ordering;

import com.edu.web.spacecatsmarket.domain.catalog.Product;
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
    Set<Product> products = new HashSet<>();
}
