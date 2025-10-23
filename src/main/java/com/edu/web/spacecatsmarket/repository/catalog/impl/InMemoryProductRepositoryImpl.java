package com.edu.web.spacecatsmarket.repository.catalog.impl;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.service.exception.ProductAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Slf4j
public class InMemoryProductRepositoryImpl implements ProductRepository {

    private static final HashMap<UUID, Product> products = new HashMap<>();

    @Override
    public void save(Product product) {
        if (products.containsKey(product.getId())) {
            log.warn("Product with id {} already exists", product.getId());
            throw new ProductAlreadyExistException("Product with id " + product.getId() + " already exists");
        }
        products.put(product.getId(), product);
    }

    @Override
    public void update(Product product) {
        products.replace(product.getId(), product);
    }

    @Override
    public void saveAll(HashMap<UUID, Product> mapOfProducts) {
        products.putAll(mapOfProducts);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public void delete(UUID id) {
        products.remove(id);
    }

    @Override
    public boolean existByName(String productName) {
        return products.values()
                .stream()
                .anyMatch(product -> product.getName().equals(productName));
    }

    @Override
    public List<Product> findAllByCategory(Category category) {
        return products.values()
                .stream()
                .filter(product -> product.getCategories().contains(category))
                .toList();
    }
}
