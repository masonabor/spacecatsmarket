package com.edu.web.spacecatsmarket.catalog.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class Product {

    ProductId id;
    ProductName name;
    String description;
    ProductAmount amount;
    Price price;

    @Builder.Default
    Set<Category> categories = new HashSet<>();

    public void setId(UUID id) {
        this.id = new ProductId(id);
    }

    public void rename(String name) {
        this.name = new ProductName(name);
    }

    public void resetDescription(String description) {
        this.description = description;
    }

    public void changePrice(Double price) {
        this.price = new Price(price);
    }

    public Product addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

    public Product removeCategory(Category category) {
        this.categories.remove(category);
        return this;    }

    public Product addToAmount(Integer amount) {
        Integer previousAmount = this.amount.amount();
        this.amount = new ProductAmount(previousAmount + amount);
        return this;
    }

    public Product removeFromAmount(Integer amount) {
        Integer previousAmount = this.amount.amount();
        this.amount = new ProductAmount(previousAmount - amount);
        return this;
    }

    public void addCategories(Set<Category> categories) {
        this.categories.addAll(categories);
    }
}
