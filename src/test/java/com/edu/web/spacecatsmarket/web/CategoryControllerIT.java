package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.AbstractIntegrationTest;
import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@DisplayName("Category Integration Tests")
class CategoryControllerIT extends AbstractIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void testGetAllCategories() throws Exception {
        categoryRepository.save(CategoryEntity.builder().name("C1").build());
        categoryRepository.save(CategoryEntity.builder().name("C2").build());

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testCreateCategory_success() throws Exception {
        CreateCategoryRequestDto request = new CreateCategoryRequestDto("Space goods");

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Space goods")));

        assertTrue(categoryRepository.existsByName("Space goods"));
    }

    @Test
    void testUpdateCategory_success() throws Exception {
        CategoryEntity saved = categoryRepository.save(CategoryEntity.builder().name("Old").build());
        UpdateCategoryRequestDto request = new UpdateCategoryRequestDto(saved.getId().toString(), "New");

        mockMvc.perform(put("/api/v1/categories/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New")));
    }

    @Test
    void testUpdateCategory_BadRequestIdMismatch() throws Exception {
        UpdateCategoryRequestDto request = new UpdateCategoryRequestDto(UUID.randomUUID().toString(), "New");
        mockMvc.perform(put("/api/v1/categories/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteCategory_success() throws Exception {
        CategoryEntity saved = categoryRepository.save(CategoryEntity.builder().name("Delete Me").build());

        mockMvc.perform(delete("/api/v1/categories/{id}", saved.getId())
                        .header("role", "admin"))
                .andExpect(status().isNoContent());

        assertFalse(categoryRepository.existsById(saved.getId()));
    }
}