package com.edu.web.spacecatsmarket.domain.ordering;

import com.edu.web.spacecatsmarket.domain.catalog.Product;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
public class Cart {

    UUID id;

    @Builder.Default
    Set<Product> items = new HashSet<>();

    UUID customerId;
}
