package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.service.ProductCatalogService;
import com.edu.web.spacecatsmarket.service.ProductService;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller Integration Tests")
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductCatalogService productCatalogService;

    @MockitoBean
    private ProductDtoMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseProductDto productResponse;

    @BeforeEach
    void setUp() {
        reset(productService, productCatalogService);
        productResponse = ResponseProductDto.builder()
                .id(UUID.randomUUID().toString())
                .name("Galaxy Phone")
                .description("Smartphone for space travelers")
                .amount(10)
                .price(999.99)
                .categories(Set.of())
                .build();
    }

    @Test
    void testGetAllProducts_success() throws Exception {
        when(productCatalogService.getAll()).thenReturn(List.of(productResponse));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Galaxy Phone"));

        verify(productCatalogService, times(1)).getAll();
    }

    @Test
    void testGetProductById_success() throws Exception {
        when(productCatalogService.getById(any())).thenReturn(productResponse);

        mockMvc.perform(get("/api/v1/products/{id}", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galaxy Phone"));

        verify(productCatalogService, times(1)).getById(any());
    }

    @Test
    void testCreateProduct_success() throws Exception {
        CreateProductRequestDto request = CreateProductRequestDto.builder()
                .name("Galaxy Phone")
                .description("Smartphone for space travelers")
                .amount(10)
                .price(999.99)
                .categoriesId(Set.of(UUID.randomUUID().toString()))
                .build();

        when(productService.createProduct(any())).thenReturn(productResponse);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galaxy Phone"));

        verify(productService, times(1)).createProduct(any());
    }

    @Test
    void testCreateProduct_invalidData_returnsBadRequest() throws Exception {
        CreateProductRequestDto invalid = CreateProductRequestDto.builder()
                .name("")
                .description("")
                .amount(-1)
                .price(null)
                .categoriesId(Set.of())
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).createProduct(any());
    }


    @Test
    void testUpdateProduct_idMismatch_returnsBadRequest() throws Exception {
        UpdateProductRequestDto request = new UpdateProductRequestDto(
                "wrong-id",
                "Updated Name",
                "Updated Description",
                5,
                500.0,
                Set.of(UUID.randomUUID().toString())
        );

        mockMvc.perform(put("/api/v1/products/{id}", "another-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).updateProduct(any());
    }

    @Test
    void testDeleteProduct_success() throws Exception {
        doNothing().when(productService).deleteProduct(any());

        mockMvc.perform(delete("/api/v1/products/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(any());
    }

}
