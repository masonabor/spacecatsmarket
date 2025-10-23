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

    public Product generateId() {
        this.id = UUID.randomUUID();
        return this;
    }

    public Product addToAmount(Integer amount) {
        this.amount += amount;
        return this;
    }

    public Product removeFromAmount(Integer amount) {
        this.amount -= amount;
        return this;
    }

    public Product addCategories(Set<Category> categories) {
        this.categories.addAll(categories);
        return this;
    }

    public Product addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public Product removeCategory(Category category) {
        this.categories.remove(category);
        return this;
    }
}
