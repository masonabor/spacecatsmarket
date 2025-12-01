package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.AbstractIntegrationTest;
import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.exception.ProductAlreadyExistException;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@DisplayName("ProductServiceImpl Tests (Testcontainers)")
public class ProductServiceIT extends AbstractIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void cleanDb() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create product successfully")
    void testCreateProduct() {

        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder().name("Cat").build()
        );

        CreateProductRequestDto request = new CreateProductRequestDto(
                "NewProduct",
                "desc",
                10,
                99.0,
                Set.of(category.getId().toString())
        );

        ResponseProductDto result = productService.createProduct(request);

        assertEquals("NewProduct", result.name());
        assertTrue(productRepository.existsByName("NewProduct"));

        ProductEntity created = productRepository.findAll().getFirst();
        assertEquals("NewProduct", created.getName());
        assertEquals(1, created.getCategories().size());
    }

    @Test
    @DisplayName("Should throw when creating product that already exists")
    void testCreateProductAlreadyExists() {
        productRepository.save(
                ProductEntity.builder()
                        .name("ProductName")
                        .description("d")
                        .amount(0)
                        .price(0.0)
                        .categories(Set.of())
                        .build()
        );

        CreateProductRequestDto request =
                new CreateProductRequestDto("ProductName", null, null, null, Set.of());

        assertThrows(ProductAlreadyExistException.class,
                () -> productService.createProduct(request));
    }

    @Test
    @DisplayName("Should throw when category not found during create")
    void testCreateProductCategoryNotFound() {

        CreateProductRequestDto request =
                new CreateProductRequestDto("Prod", "d", 1, 1.0, Set.of(UUID.randomUUID().toString()));

        assertThrows(CategoryNotFoundException.class,
                () -> productService.createProduct(request));
    }

    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProduct() {

        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder().name("Cat").build()
        );

        ProductEntity saved = productRepository.save(
                ProductEntity.builder()
                        .name("Old")
                        .description("d")
                        .amount(10)
                        .price(10.0)
                        .categories(Set.of())
                        .build()
        );

        UpdateProductRequestDto request = new UpdateProductRequestDto(
                saved.getId().toString(),
                "Updated",
                "desc",
                5,
                50.0,
                Set.of(category.getId().toString())
        );

        ResponseProductDto result = productService.updateProduct(request);

        assertEquals("Updated", result.name());

        ProductEntity updated = productRepository.findById(saved.getId()).orElseThrow();

        assertEquals("Updated", updated.getName());
        assertEquals(1, updated.getCategories().size());
    }

    @Test
    @DisplayName("Should delete product successfully")
    void testDeleteProduct() {
        ProductEntity saved = productRepository.save(
                ProductEntity.builder()
                        .name("Del")
                        .description("d")
                        .amount(1)
                        .price(5.0)
                        .categories(Set.of())
                        .build()
        );

        productService.deleteProduct(saved.getId());

        assertFalse(productRepository.existsById(saved.getId()));
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when deleting unknown id")
    void testDeleteProductNotFound() {
        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(UUID.randomUUID()));
    }
}
