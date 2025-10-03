package com.edu.web.spacecatsmarket.catalog.infrastructure.impl;

import com.edu.web.spacecatsmarket.catalog.domain.*;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Repository
@Slf4j
public class InMemoryProductRepositoryImpl implements ProductRepository {

    private static final HashMap<ProductId, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        if (products.containsKey(product.getId())) {
            log.warn("Product with id {} already exists", product.getId());
            products.replace(product.getId(), product);
            return;
        }
        products.put(product.getId(), product);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public void delete(ProductId id) {
        if (!products.containsKey(id)) {
            throw new ProductNotFoundException("product with id " + id + " not found");
        }
        products.remove(id);
    }

    @Override
    public boolean existByName(ProductName productName) {
        return products.values().stream()
                .anyMatch(product -> product.getName().equals(productName));
    }

    // TODO
    @Override
    public List<Product> findAllByCategory(Category category) {
        return null;
    }

}
