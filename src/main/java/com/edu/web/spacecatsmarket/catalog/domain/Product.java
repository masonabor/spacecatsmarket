package com.edu.web.spacecatsmarket.catalog.domain;

import lombok.Builder;
import lombok.Getter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class Product {

    ProductId id;
    ProductName name;
    String description;
    Integer amount;
    Price price;

    @Builder.Default
    Set<Category> categories = new HashSet<>();

    protected void rename(String name) {
        this.name = new ProductName(name);
    }

    protected void resetDescription(String description) {
        this.description = description;
    }

    protected void changePrice(Double price) {
        this.price = new Price(price);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    public void addToAmount(Integer amount) {
        this.amount += amount;
    }

    public void removeFromAmount(Integer amount) {
        this.amount -= amount;
    }
}
