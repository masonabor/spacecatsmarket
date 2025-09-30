package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.ProductCatalogService;
import com.edu.web.spacecatsmarket.catalog.domain.Product;
import com.edu.web.spacecatsmarket.catalog.domain.ProductId;
import com.edu.web.spacecatsmarket.catalog.domain.ProductRepository;
import com.edu.web.spacecatsmarket.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductRepository productRepository;

    public Product getById(UUID id) {
        return productRepository.findById(new ProductId(id)).orElseThrow(() -> {
            log.info("Product with id {} not found", id);
            return new ProductNotFoundException("product not found");
        });
    }

    public List<Product> getAll() {
        return Optional.of(productRepository.findAll()).orElseThrow(() -> {
            log.info("All products not found");
            return new ProductNotFoundException("all products not found");
        });
    }

//    public List<Product> getAllByCategoryName(String categoryName) {
//        CategoryName category = new CategoryName(categoryName);
//
//        return productRepository.findAll().stream()
//                .filter(product -> product.getCategories().contains(category))
//                .toList();
//    }
}
