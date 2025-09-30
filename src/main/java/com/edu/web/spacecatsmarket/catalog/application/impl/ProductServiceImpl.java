package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.ProductService;
import com.edu.web.spacecatsmarket.catalog.domain.Product;
import com.edu.web.spacecatsmarket.dto.product.CreateProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductDto;

import java.util.UUID;

public class ProductServiceImpl implements ProductService {

    @Override
    public Product createProduct(CreateProductDto createProductDto) {
        return null;
    }

    @Override
    public void deleteProduct(UUID productId) {

    }

    @Override
    public Product updateProduct(UpdateProductDto updateProductDto) {
        return null;
    }
}
