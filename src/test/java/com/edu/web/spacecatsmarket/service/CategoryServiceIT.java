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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Category Service Tests (Testcontainers)")
@Transactional
public class CategoryServiceIT extends AbstractIntegrationTest {

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
        CategoryEntity saved = categoryRepository.save(CategoryEntity.builder().name("CATEGORY_NAME").build());
        ResponseCategoryDto result = categoryService.getById(saved.getId());
        assertEquals(saved.getId().toString(), result.id());
    }

    @Test
    @DisplayName("Should throw if category not found")
    void testGetByIdNotFound() {
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Should get all categories")
    void testGetAll() {
        categoryRepository.save(CategoryEntity.builder().name("Cat1").build());
        categoryRepository.save(CategoryEntity.builder().name("Cat2").build());

        List<ResponseCategoryDto> list = categoryService.getAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should update category successfully")
    void testUpdateCategory() {
        CategoryEntity saved = categoryRepository.save(CategoryEntity.builder().name("Old").build());
        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto(saved.getId().toString(), "Updated");
        ResponseCategoryDto result = categoryService.updateCategory(dto);
        assertEquals("Updated", result.name());
    }

    @Test
    @DisplayName("Should throw if updating to existing name")
    void testUpdateCategoryDuplicate() {
        CategoryEntity c1 = categoryRepository.save(CategoryEntity.builder().name("Cat1").build());
        categoryRepository.save(CategoryEntity.builder().name("Cat2").build());

        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto(c1.getId().toString(), "Cat2");
        assertThrows(CategoryAlreadyExistException.class, () -> categoryService.updateCategory(dto));
    }

    @Test
    @DisplayName("Should delete category and remove relation from product")
    void testDeleteCategoryWithProduct() {
        CategoryEntity category = categoryRepository.save(CategoryEntity.builder().name("To Delete").build());

        ProductEntity product = ProductEntity.builder()
                .name("Prod")
                .description("d")
                .amount(1)
                .price(10.0)
                .categories(new HashSet<>(Set.of(category)))
                .build();
        productRepository.save(product);

        categoryService.delete(category.getId());

        assertFalse(categoryRepository.existsById(category.getId()));

        ProductEntity updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertTrue(updatedProduct.getCategories().isEmpty());
    }

    @Test
    @DisplayName("Should throw if category not found on delete")
    void testDeleteNotFound() {
        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(UUID.randomUUID()));
    }
}