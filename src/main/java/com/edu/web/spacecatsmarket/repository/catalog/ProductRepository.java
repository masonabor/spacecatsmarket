package com.edu.web.spacecatsmarket.repository.catalog;


import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    void save(Product product);
    void update(Product product);
    void saveAll(HashMap<UUID, Product> products);
    List<Product> findAll();
    Optional<Product> findById(UUID id);
    void delete(UUID id);
    boolean existByName(String productName);
    List<Product> findAllByCategory(Category category);
    Product generateId(Product product);
    void addToAmount(UUID productId, int amount);
    void removeFromAmount(UUID productId, int amount);
    void addCategory(UUID productId, Category category);
    void removeCategory(UUID productId, Category category);
}
