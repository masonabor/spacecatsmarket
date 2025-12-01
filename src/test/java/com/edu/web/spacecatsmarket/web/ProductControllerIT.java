package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.AbstractIntegrationTest;
import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@DisplayName("Product Integration Tests")
class ProductControllerIT extends AbstractIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /api/v1/products - Should return all products")
    void testGetAllProducts() throws Exception {
        productRepository.save(ProductEntity.builder().name("P1").description("d").amount(1).price(10.0).categories(new HashSet<>()).build());
        productRepository.save(ProductEntity.builder().name("P2").description("d").amount(1).price(10.0).categories(new HashSet<>()).build());

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("GET /api/v1/products/{id} - Should return product by ID")
    void testGetById() throws Exception {
        ProductEntity saved = productRepository.save(ProductEntity.builder().name("FindMe").description("d").amount(1).price(10.0).categories(new HashSet<>()).build());

        mockMvc.perform(get("/api/v1/products/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("FindMe")));
    }

    @Test
    @DisplayName("POST /api/v1/products - Should create product successfully")
    void testCreateProduct_success() throws Exception {
        CategoryEntity cat = categoryRepository.save(CategoryEntity.builder().name("Food").build());
        CreateProductRequestDto request = new CreateProductRequestDto("Galaxy Tuna", "Fresh", 10, 55.5, Set.of(cat.getId().toString()));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Galaxy Tuna")));

        assertTrue(productRepository.existsByName("Galaxy Tuna"));
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - Should update product successfully")
    void testUpdateProduct_success() throws Exception {
        ProductEntity saved = productRepository.save(ProductEntity.builder().name("galaxy Old").description("galaxy d").amount(1).price(10.0).categories(new HashSet<>()).build());
        UpdateProductRequestDto request = new UpdateProductRequestDto(saved.getId().toString(), "galaxy Valid Product", "galaxy New Desc", 5, 20.0, Set.of());

        mockMvc.perform(put("/api/v1/products/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("galaxy Valid Product")));
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - Should return 400 if IDs mismatch")
    void testUpdateProduct_BadRequestIdMismatch() throws Exception {
        UUID correctId = UUID.randomUUID();
        UUID mismatchId = UUID.randomUUID();

        UpdateProductRequestDto request = new UpdateProductRequestDto(correctId.toString(), "N", "D", 1, 1.0, Set.of());

        mockMvc.perform(put("/api/v1/products/{id}", mismatchId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/v1/products/{id} - Should return 404 if product not found")
    void testUpdateProduct_NotFound() throws Exception {
        UUID randomId = UUID.randomUUID();
        UpdateProductRequestDto request = new UpdateProductRequestDto(randomId.toString(), "galaxy Ghost", "D", 1, 1.0, Set.of());

        mockMvc.perform(put("/api/v1/products/{id}", randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/products/{id} - Should delete product successfully")
    void testDeleteProduct() throws Exception {
        ProductEntity saved = productRepository.save(ProductEntity.builder().name("Delete Me").description("d").amount(1).price(10.0).categories(new HashSet<>()).build());

        mockMvc.perform(delete("/api/v1/products/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        assertFalse(productRepository.existsById(saved.getId()));
    }
}