package com.edu.web.spacecatsmarket.service.impl;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.service.ProductService;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.exception.ProductAlreadyExistException;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
    public ResponseProductDto createProduct(CreateProductRequestDto createProductRequestDto) {
        if (productRepository.existByName(createProductRequestDto.name())) {
            log.warn("Product with name {} already exists", createProductRequestDto.name());
            throw new ProductAlreadyExistException("Product with name " + createProductRequestDto.name() + " already exists");
        }
        Product product = mapper.toProduct(createProductRequestDto);
        product.generateId()
                .addCategories(fromNameToCategory(createProductRequestDto.categoriesId()));
        productRepository.save(product);
        log.info("New product created");

        return mapper.toResponseProductDto(product);
    }

    @Override
    public void deleteProduct(UUID productId) throws ProductNotFoundException {
        productRepository.delete(productId);
        log.info("Deleted product with id {}", productId);
    }

    @Override
    public ResponseProductDto updateProduct(UpdateProductRequestDto updateProductRequestDto) {
        if (productRepository.existByName(updateProductRequestDto.name())) {
            throw new ProductAlreadyExistException("Product with name " + updateProductRequestDto.name() + " already exists");
        }
        Product product = mapper.toProduct(updateProductRequestDto);
        product.addCategories(fromNameToCategory(updateProductRequestDto.categoriesId()));
        productRepository.update(product);
        log.info("Product with id {} updated", product.getId());
        return mapper.toResponseProductDto(product);
    }

    private Set<Category> fromNameToCategory(Set<String> categoriesId) {
        Set<Category> productCategories = new HashSet<>();

        for (String categoryId : categoriesId) {
            productCategories.add(categoryRepository.findById(UUID.fromString(categoryId)).orElseThrow(() -> {
                log.warn("Category with id {} not found", categoryId);
                return new CategoryNotFoundException("Category with id " + categoryId + " not found");
            }));
        }

        return productCategories;
    }
}
