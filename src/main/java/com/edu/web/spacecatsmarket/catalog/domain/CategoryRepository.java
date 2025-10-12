package com.edu.web.spacecatsmarket.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    void deleteById(CategoryId categoryId);
    Optional<Category> findById(CategoryId categoryId);
    List<Category> findAll();
    Optional<Category> findByName(CategoryName categoryName);
    boolean existsById(CategoryId categoryId);
}

