package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.AbstractIntegrationTest;
import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import com.edu.web.spacecatsmarket.service.exception.CategoryAlreadyExistException;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Category Service Tests (Testcontainers)")
public class CategoryServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanDb() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create category when name unique")
    void testCreateCategory() {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto("CATEGORY_NAME");

        ResponseCategoryDto result = categoryService.createCategory(dto);

        assertNotNull(result);
        assertEquals("CATEGORY_NAME", result.name());
        assertTrue(categoryRepository.existsByName("CATEGORY_NAME"));
    }

    @Test
    @DisplayName("Should throw exception if category exists")
    void testCreateCategoryExists() {
        categoryRepository.save(CategoryEntity.builder().name("CATEGORY_NAME").build());

        CreateCategoryRequestDto dto = new CreateCategoryRequestDto("CATEGORY_NAME");

        assertThrows(CategoryAlreadyExistException.class, () -> categoryService.createCategory(dto));
    }

    @Test
    @DisplayName("Should get category by id")
    void testGetById() {
        CategoryEntity saved = categoryRepository.save(
                CategoryEntity.builder().name("CATEGORY_NAME").build()
        );

        ResponseCategoryDto result = categoryService.getById(saved.getId());

        assertNotNull(result);
        assertEquals(saved.getId().toString(), result.id());
        assertEquals("CATEGORY_NAME", result.name());
    }

    @Test
    @DisplayName("Should throw if category not found")
    void testGetByIdNotFound() {
        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should update category successfully")
    void testUpdateCategory() {
        CategoryEntity saved = categoryRepository.save(
                CategoryEntity.builder().name("Old").build()
        );

        UpdateCategoryRequestDto dto =
                new UpdateCategoryRequestDto(saved.getId().toString(), "Updated");

        ResponseCategoryDto result = categoryService.updateCategory(dto);

        assertEquals("Updated", result.name());

        CategoryEntity reloaded = categoryRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Updated", reloaded.getName());
    }


    @Test
    @DisplayName("Should throw if category not found on delete")
    void testDeleteNotFound() {
        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.delete(UUID.randomUUID()));
    }
}
