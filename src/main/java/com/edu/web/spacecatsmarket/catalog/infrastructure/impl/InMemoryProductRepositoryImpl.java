package com.edu.web.spacecatsmarket.catalog.infrastructure.impl;

import com.edu.web.spacecatsmarket.catalog.domain.Product;
import com.edu.web.spacecatsmarket.catalog.domain.ProductId;
import com.edu.web.spacecatsmarket.catalog.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Repository
public class InMemoryProductRepositoryImpl implements ProductRepository {

    private static final HashMap<ProductId, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        if (products.containsKey(product.getId())) {
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
        return Optional.of(products.get(id));
    }

    @Override
    public void delete(Product product) {
        products.remove(product.getId());
    }
}
