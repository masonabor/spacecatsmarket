package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.ProductService;
import com.edu.web.spacecatsmarket.catalog.application.mapper.ProductDtoMapper;
import com.edu.web.spacecatsmarket.catalog.domain.Product;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.catalog.domain.ProductId;
import com.edu.web.spacecatsmarket.catalog.domain.ProductRepository;
import com.edu.web.spacecatsmarket.exceptions.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public Product createProduct(@Valid CreateProductDto createProductDto) {
        Product product = productDtoMapper.toProduct(createProductDto);
        productRepository.save(product);
        log.info("New product created");
        return product;
    }

    @Override
    public void deleteProduct(UUID productId) throws ProductNotFoundException {
        productRepository.delete(new ProductId(productId));
    }

    @Override
    public Product updateProduct(@Valid UpdateProductDto updateProductDto) {
        Product product = productDtoMapper.toProduct(updateProductDto);
        productRepository.save(product);
        log.info("Product with id {} updated", product.getId());
        return product;
    }
}
