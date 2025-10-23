package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;

import java.util.UUID;

public interface ProductService {

    ResponseProductDto createProduct(CreateProductRequestDto createProductRequestDto);
    void deleteProduct(UUID productId);
    ResponseProductDto updateProduct(UpdateProductRequestDto updateProductRequestDto);
}
