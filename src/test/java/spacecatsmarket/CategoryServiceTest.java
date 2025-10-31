package com.edu.web.spacecatsmarket;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.service.exception.CategoryAlreadyExistException;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.impl.CategoryServiceImpl;
import com.edu.web.spacecatsmarket.service.mapper.CategoryDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CategoryServiceImpl.class})
@Import(CategoryDtoMapper.class)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Category Service Tests")
public class CategoryServiceTest {

    //mock data
    private static final UUID CATEGORY_ID = UUID.randomUUID();
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final Category CATEGORY = Category.builder().id(CATEGORY_ID).name(CATEGORY_NAME).build();

    @MockitoBean
    private CategoryRepository categoryRepository;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private CategoryDtoMapper categoryMapper;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Should create category when name unique")
    void testCreateCategory() {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto(CATEGORY_NAME);
        when(categoryRepository.existsByName(CATEGORY_NAME)).thenReturn(false);
        when(categoryMapper.toCategory(dto)).thenReturn(CATEGORY);
        categoryService.createCategory(dto);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should throw exception if category exists")
    void testCreateCategoryExists() {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto(CATEGORY_NAME);
        when(categoryRepository.existsByName(CATEGORY_NAME)).thenReturn(true);
        assertThrows(CategoryAlreadyExistException.class, () -> categoryService.createCategory(dto));
    }

    @Test
    @DisplayName("Should get category by id")
    void testGetById() {
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(CATEGORY));
        when(categoryMapper.toResponseCategoryDto(CATEGORY))
                .thenReturn(new ResponseCategoryDto(CATEGORY_ID.toString(), CATEGORY.getName()));

        ResponseCategoryDto result = categoryService.getById(CATEGORY_ID);
        assertNotNull(result);

        verify(categoryRepository).findById(CATEGORY_ID);
        verify(categoryMapper).toResponseCategoryDto(CATEGORY);
    }

    @Test
    @DisplayName("Should throw if category not found")
    void testGetByIdNotFound() {
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getById(CATEGORY_ID));
    }

    @Test
    @DisplayName("Should update category successfully")
    void testUpdateCategory() {
        UpdateCategoryRequestDto dto = new UpdateCategoryRequestDto(CATEGORY_ID.toString(), "Updated");
        Category updated = Category.builder().id(CATEGORY_ID).name("Updated").build();
        Product product = Product.builder().id(UUID.randomUUID()).name("Product").build();

        when(categoryMapper.toCategory(dto)).thenReturn(updated);
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(CATEGORY));
        when(productRepository.findAllByCategory(CATEGORY)).thenReturn(List.of(product));

        categoryService.updateCategory(dto);

        verify(productRepository).removeCategory(product.getId(), CATEGORY);
        verify(productRepository).addCategory(product.getId(), updated);
        verify(categoryRepository).save(updated);
    }

    @Test
    @DisplayName("Should delete category successfully")
    void testDeleteCategory() {
        Product product = Product.builder().id(UUID.randomUUID()).name("Product").build();
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(CATEGORY));
        when(productRepository.findAllByCategory(CATEGORY)).thenReturn(List.of(product));

        categoryService.delete(CATEGORY_ID);

        verify(productRepository).removeCategory(product.getId(), CATEGORY);
        verify(categoryRepository).delete(CATEGORY_ID);
    }

    @Test
    @DisplayName("Should do nothing if category not found on delete")
    void testDeleteNotFound() {
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());
        categoryService.delete(CATEGORY_ID);
        verify(categoryRepository).findById(CATEGORY_ID);
        verifyNoMoreInteractions(productRepository);
    }
}