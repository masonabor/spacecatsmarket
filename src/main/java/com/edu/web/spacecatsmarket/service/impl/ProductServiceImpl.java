package com.edu.web.spacecatsmarket.service.impl;

import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import com.edu.web.spacecatsmarket.service.ProductService;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.exception.ProductAlreadyExistException;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductDtoMapper mapper;

    @Override
    @Transactional
    public ResponseProductDto createProduct(CreateProductRequestDto createProductRequestDto) {
        if (productRepository.existsByName(createProductRequestDto.name())) {
            log.warn("Product with name {} already exists", createProductRequestDto.name());
            throw new ProductAlreadyExistException("Product with name " + createProductRequestDto.name() + " already exists");
        }

        ProductEntity product = mapper.toProductEntity(createProductRequestDto);

        Set<CategoryEntity> categories = fetchCategoriesByIds(createProductRequestDto.categoriesId());

        product.setCategories(categories);

        ProductEntity savedProduct = productRepository.save(product);

        log.info("New product created: {}", savedProduct.getName());
        return mapper.toResponseProductDto(savedProduct);
    }

    @Override
    @Transactional
    public ResponseProductDto updateProduct(UpdateProductRequestDto updateProductRequestDto) {
        UUID productId = UUID.fromString(updateProductRequestDto.id());

        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

        if (!existingProduct.getName().equals(updateProductRequestDto.name())
                && productRepository.existsByName(updateProductRequestDto.name())) {
            throw new ProductAlreadyExistException("Product with name " + updateProductRequestDto.name() + " already exists");
        }

        existingProduct.setName(updateProductRequestDto.name());
        existingProduct.setDescription(updateProductRequestDto.description());
        existingProduct.setPrice(updateProductRequestDto.price());
        existingProduct.setAmount(updateProductRequestDto.amount());

        Set<CategoryEntity> newCategories = fetchCategoriesByIds(updateProductRequestDto.categoriesId());
        existingProduct.setCategories(newCategories);

        ProductEntity savedProduct = productRepository.save(existingProduct);

        log.info("Product with id {} updated", savedProduct.getId());
        return mapper.toResponseProductDto(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found: " + productId);
        }
        productRepository.deleteById(productId);
        log.info("Deleted product with id {}", productId);
    }

    private Set<CategoryEntity> fetchCategoriesByIds(Set<String> categoriesId) {
        if (categoriesId == null || categoriesId.isEmpty()) {
            return new HashSet<>();
        }

        Set<UUID> uuids = categoriesId.stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());

        List<CategoryEntity> categories = categoryRepository.findAllById(uuids);

        if (categories.size() != uuids.size()) {
            throw new CategoryNotFoundException("One or more categories not found");
        }

        return new HashSet<>(categories);
    }
}