package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;

import java.util.UUID;

public interface ProductService {

    ResponseProductDto createProduct(CreateProductDto createProductDto);
    void deleteProduct(UUID productId);
    ResponseProductDto updateProduct(UpdateProductDto updateProductDto);
}
