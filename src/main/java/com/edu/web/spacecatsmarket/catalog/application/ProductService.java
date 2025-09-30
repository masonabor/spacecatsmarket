package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.catalog.domain.Product;
import com.edu.web.spacecatsmarket.dto.product.CreateProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductDto;

import java.util.UUID;

public interface ProductService {

    Product createProduct(CreateProductDto createProductDto);
    void deleteProduct(UUID productId);
    Product updateProduct(UpdateProductDto updateProductDto);
}
