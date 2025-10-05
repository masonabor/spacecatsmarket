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
    public void save(Category category) {
        categories.put(category.getId(), category);
    }

    @Override
    public void delete(CategoryId categoryId) {
        if (!categories.containsKey(categoryId)) {
            throw new CategoryNotFoundException("category with id " + categoryId + " not found");
        }
        categories.remove(categoryId);
    }

    @Override
    public Optional<Category> findById(CategoryId categoryId) {
        return Optional.of(categories.get(categoryId));
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public boolean existsByName(CategoryName categoryName) {
        return categories.values().stream()
                .anyMatch(category -> category.getName().equals(categoryName));
    }
}
