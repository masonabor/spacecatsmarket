package com.edu.web.spacecatsmarket.service.impl;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.service.ProductService;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.exception.ProductAlreadyExistException;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
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

        Set<Category> categories = fromIdsToCategories(createProductRequestDto.categoriesId());
        for (Category category : categories) {
            productRepository.addCategory(product.getId(), category);
        }

        productRepository.save(product);
        log.info("New product created: {}", product.getName());

        return mapper.toResponseProductDto(product);
    }

    @Override
    public void deleteProduct(UUID productId) throws ProductNotFoundException {
        if (productRepository.findById(productId).isEmpty()) {
            throw new ProductNotFoundException("Product not found: " + productId);
        }
        productRepository.delete(productId);
        log.info("Deleted product with id {}", productId);
    }

    @Override
    public ResponseProductDto updateProduct(UpdateProductRequestDto updateProductRequestDto) {
        if (productRepository.existByName(updateProductRequestDto.name())) {
            throw new ProductAlreadyExistException("Product with name " + updateProductRequestDto.name() + " already exists");
        }

        Product product = mapper.toProduct(updateProductRequestDto);
        Set<Category> categories = fromIdsToCategories(updateProductRequestDto.categoriesId());
        for (Category category : categories) {
            productRepository.addCategory(product.getId(), category);
        }

        productRepository.update(product);
        log.info("Product with id {} updated", product.getId());
        return mapper.toResponseProductDto(product);
    }

    private Set<Category> fromIdsToCategories(Set<String> categoriesId) {
        Set<Category> categories = new HashSet<>();
        for (String id : categoriesId) {
            Category category = categoryRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> {
                        log.warn("Category with id {} not found", id);
                        return new CategoryNotFoundException("Category with id " + id + " not found");
                    });
            categories.add(category);
        }
        return categories;
    }
}
