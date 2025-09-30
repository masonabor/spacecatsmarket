package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.catalog.domain.Product;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;

import java.util.UUID;

public interface ProductService {

    Product createProduct(CreateProductDto createProductDto);
    void deleteProduct(UUID productId);
    Product updateProduct(UpdateProductDto updateProductDto);
}
