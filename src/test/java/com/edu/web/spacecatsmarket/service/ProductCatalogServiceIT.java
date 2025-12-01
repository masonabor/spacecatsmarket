package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.AbstractIntegrationTest;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.service.impl.ProductCatalogServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("ProductCatalog Service Tests (Testcontainers)")
public class ProductCatalogServiceIT extends AbstractIntegrationTest {

    @Autowired
    private ProductCatalogServiceImpl productCatalogService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanDb() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get product by id")
    void testGetById() {

        ProductEntity saved = productRepository.save(
                ProductEntity.builder()
                        .name("Product1")
                        .description("d")
                        .amount(10)
                        .price(100.0)
                        .categories(new HashSet<>())
                        .build()
        );

        ResponseProductDto result = productCatalogService.getById(saved.getId());

        assertNotNull(result);
        assertEquals(saved.getId().toString(), result.id());
        assertEquals("Product1", result.name());
    }

    @Test
    @DisplayName("Should throw when product not found by id")
    void testGetByIdNotFound() {
        assertThrows(ProductNotFoundException.class,
                () -> productCatalogService.getById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should get all products")
    void testGetAll() {

        productRepository.save(
                ProductEntity.builder()
                        .name("Product1")
                        .description("d1")
                        .amount(5)
                        .price(50.0)
                        .categories(new HashSet<>())
                        .build()
        );

        productRepository.save(
                ProductEntity.builder()
                        .name("Product2")
                        .description("d2")
                        .amount(3)
                        .price(20.0)
                        .categories(new HashSet<>())
                        .build()
        );

        List<ResponseProductDto> result = productCatalogService.getAll();

        assertEquals(2, result.size());
    }
}
