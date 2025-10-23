package com.edu.web.spacecatsmarket.domain.catalog;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Category {

    UUID id;
    String name;

    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
