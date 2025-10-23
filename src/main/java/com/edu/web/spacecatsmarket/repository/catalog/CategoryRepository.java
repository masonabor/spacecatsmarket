package com.edu.web.spacecatsmarket.repository.catalog;


import com.edu.web.spacecatsmarket.domain.catalog.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

    void save(Category category);
    void delete(UUID categoryId);
    Optional<Category> findById(UUID categoryId);
    List<Category> findAll();
    boolean existsByName(String categoryName);
}
