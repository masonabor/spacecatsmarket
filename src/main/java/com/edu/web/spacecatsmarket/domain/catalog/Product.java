package com.edu.web.spacecatsmarket.domain.catalog;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class Product {

    UUID id;
    String name;
    String description;
    Integer amount;
    Double price;

    @Builder.Default
    Set<Category> categories = new HashSet<>();
}
