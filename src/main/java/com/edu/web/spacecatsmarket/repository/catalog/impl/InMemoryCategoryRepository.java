package com.edu.web.spacecatsmarket.repository.catalog.impl;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

    private static final Map<UUID, Category> categories = new HashMap<>();

    @Override
    public void save(Category category) {
        categories.put(category.getId(), category);
    }

    @Override
    public void delete(UUID categoryId) {
        if (!categories.containsKey(categoryId)) {
            throw new CategoryNotFoundException("category with id " + categoryId + " not found");
        }
        categories.remove(categoryId);
    }

    @Override
    public Optional<Category> findById(UUID categoryId) {
        return Optional.of(categories.get(categoryId));
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public boolean existsByName(String categoryName) {
        return categories.values().stream()
                .anyMatch(category -> category.getName().equals(categoryName));
    }
}
