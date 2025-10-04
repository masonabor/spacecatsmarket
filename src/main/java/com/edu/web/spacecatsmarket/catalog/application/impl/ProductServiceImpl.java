package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.ProductService;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.ProductAlreadyExistException;
import com.edu.web.spacecatsmarket.catalog.application.mapper.ProductDtoMapper;
import com.edu.web.spacecatsmarket.catalog.domain.*;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper mapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseProductDto createProduct(CreateProductDto createProductDto) {
        if (productRepository.existByName(new ProductName(createProductDto.name()))) {
            throw new ProductAlreadyExistException("Product with name " + createProductDto.name() + " already exists");
        }
        Product product = mapper.toDomain(createProductDto);
        product.addCategories(fromNameToCategory(createProductDto.categories()));
        productRepository.save(product);
        log.info("New product created");
        return mapper.toResponseDto(product);
    }

    @Override
    public void deleteProduct(UUID productId) throws ProductNotFoundException {
        productRepository.delete(new ProductId(productId));
        log.info("Deleted product with id {}", productId);
    }

    @Override
    public ResponseProductDto updateProduct(UpdateProductDto updateProductDto) {
        if (productRepository.existByName(new ProductName(updateProductDto.name()))) {
            throw new ProductAlreadyExistException("Product with name " + updateProductDto.name() + " already exists");
        }
        Product product = mapper.toDomain(updateProductDto);
        product.addCategories(fromNameToCategory(updateProductDto.categories()));
        productRepository.save(product);
        log.info("Product with id {} updated", product.getId());
        return mapper.toResponseDto(product);
    }

    // TODO optimization
    private Set<Category> fromNameToCategory(Set<String> categories) {
        Set<Category> productCategories = new HashSet<>();

        List<Category> existsCategories = categoryRepository.findAll();

        List<String> existsCategoriesNames= existsCategories.stream()
                .map(category -> category.getName().name())
                .toList();

        for (String name : categories) {
            if (existsCategoriesNames.contains(name))
                productCategories.add(existsCategories.stream().
                        filter(category -> category.getName().name().equals(name))
                        .findFirst()
                        .orElseThrow());
        }

        return productCategories;
    }

}
