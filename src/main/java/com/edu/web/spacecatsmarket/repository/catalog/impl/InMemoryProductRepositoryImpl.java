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

    public Product generateId(Product product) {
        Product updated = Product.builder()
                .id(UUID.randomUUID())
                .name(product.getName())
                .description(product.getDescription())
                .amount(product.getAmount())
                .price(product.getPrice())
                .categories(product.getCategories())
                .build();
        products.put(updated.getId(), updated);
        return updated;
    }

    public void addToAmount(UUID productId, int amount) {
        Product product = products.get(productId);
        if (product != null) {
            Product updated = Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .amount(product.getAmount() + amount)
                    .price(product.getPrice())
                    .categories(product.getCategories())
                    .build();
            products.put(productId, updated);
        }
    }

    public void removeFromAmount(UUID productId, int amount) {
        Product product = products.get(productId);
        if (product != null) {
            int newAmount = Math.max(0, product.getAmount() - amount);
            Product updated = Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .amount(newAmount)
                    .price(product.getPrice())
                    .categories(product.getCategories())
                    .build();
            products.put(productId, updated);
        }
    }

    public void addCategory(UUID productId, Category category) {
        Product product = products.get(productId);
        if (product != null) {
            Set<Category> updatedCategories = new HashSet<>(product.getCategories());
            updatedCategories.add(category);
            Product updated = Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .amount(product.getAmount())
                    .price(product.getPrice())
                    .categories(updatedCategories)
                    .build();
            products.put(productId, updated);
        }
    }

    public void removeCategory(UUID productId, Category category) {
        Product product = products.get(productId);
        if (product != null) {
            Set<Category> updatedCategories = new HashSet<>(product.getCategories());
            updatedCategories.remove(category);
            Product updated = Product.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .amount(product.getAmount())
                    .price(product.getPrice())
                    .categories(updatedCategories)
                    .build();
            products.put(productId, updated);
        }
    }
}