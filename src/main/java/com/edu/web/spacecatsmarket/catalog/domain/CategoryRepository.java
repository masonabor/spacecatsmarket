package com.edu.web.spacecatsmarket.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    void save(Category category);
    void delete(CategoryId categoryId);
    Optional<Category> findById(CategoryId categoryId);
    List<Category> findAll();
    boolean existsByName(CategoryName categoryName);
}
