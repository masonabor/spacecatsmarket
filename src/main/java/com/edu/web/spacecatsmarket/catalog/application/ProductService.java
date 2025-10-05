package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface ProductService {

    ResponseProductDto createProduct(@Valid CreateProductDto createProductDto);
    void deleteProduct(UUID productId);
    ResponseProductDto updateProduct(@Valid UpdateProductDto updateProductDto);
}
