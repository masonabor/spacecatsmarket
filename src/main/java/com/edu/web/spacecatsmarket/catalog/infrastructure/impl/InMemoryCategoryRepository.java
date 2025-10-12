package com.edu.web.spacecatsmarket.catalog.infrastructure.impl;

import com.edu.web.spacecatsmarket.catalog.domain.Category;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryId;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryName;
import com.edu.web.spacecatsmarket.catalog.domain.CategoryRepository;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

    private static final Map<CategoryId, Category> categories = new HashMap<>();

    @Override
    public Category save(Category category) {
        categories.put(category.id(), category);
        return category;
    }

    @Override
    public void deleteById(CategoryId categoryId) {
        if (!categories.containsKey(categoryId)) {
            throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
        }
        categories.remove(categoryId);
    }

    @Override
    public Optional<Category> findById(CategoryId categoryId) {
        return Optional.ofNullable(categories.get(categoryId));
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Optional<Category> findByName(CategoryName categoryName) {
        return categories.values().stream()
                .filter(category -> category.name().equals(categoryName))
                .findFirst();
    }

    @Override
    public boolean existsById(CategoryId categoryId) {
        return categories.containsKey(categoryId);
    }
}
