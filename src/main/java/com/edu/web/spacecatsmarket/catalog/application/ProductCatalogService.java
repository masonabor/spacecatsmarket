package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.catalog.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductCatalogService {

    Product getById(UUID id);
    List<Product> getAll();
}
