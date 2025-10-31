package com.edu.web.spacecatsmarket;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.exception.ProductAlreadyExistException;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.service.impl.ProductServiceImpl;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ProductServiceImpl.class})
@Import(ProductDtoMapper.class)
@DisplayName("ProductServiceImpl Tests")
public class ProductServiceTest {

    //mock data
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private CategoryRepository categoryRepository;

    @MockitoBean
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Should create product successfully")
    void testCreateProduct() {
        CreateProductRequestDto request = new CreateProductRequestDto(
                "NewProduct", "desc", 10, 99.0, Set.of(CATEGORY_ID.toString())
        );
        Product product = Product.builder().id(PRODUCT_ID).name("NewProduct").build();
        Category category = Category.builder().id(CATEGORY_ID).name("Cat").build();

        when(productRepository.existByName("NewProduct")).thenReturn(false);
        when(productDtoMapper.toProduct(request)).thenReturn(product);
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(productDtoMapper.toResponseProductDto(product)).thenReturn(
                new ResponseProductDto(PRODUCT_ID.toString(), "NewProduct", "desc", 10, 99.0, Set.of())
        );

        ResponseProductDto result = productService.createProduct(request);

        assertEquals("NewProduct", result.name());
        verify(productRepository).save(product);
        verify(productRepository).addCategory(product.getId(), category);
    }

    @Test
    @DisplayName("Should throw when creating product that already exists")
    void testCreateProductAlreadyExists() {
        CreateProductRequestDto request = new CreateProductRequestDto("ProductName", null, null, null, Set.of());
        when(productRepository.existByName("ProductName")).thenReturn(true);
        assertThrows(ProductAlreadyExistException.class, () -> productService.createProduct(request));
        verify(productRepository).existByName("ProductName");
    }

    @Test
    @DisplayName("Should throw when category not found during create")
    void testCreateProductCategoryNotFound() {
        CreateProductRequestDto request = new CreateProductRequestDto("ProductName", null, null, null, Set.of(CATEGORY_ID.toString()));
        Product product = Product.builder().id(PRODUCT_ID).name("ProductName").build();
        when(productRepository.existByName("ProductName")).thenReturn(false);
        when(productDtoMapper.toProduct(request)).thenReturn(product);
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(request));
    }

    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProduct() {
        UpdateProductRequestDto request = new UpdateProductRequestDto(
                PRODUCT_ID.toString(), "Updated", "desc", 5, 50.0, Set.of(CATEGORY_ID.toString())
        );
        Product product = Product.builder().id(PRODUCT_ID).name("Updated").build();
        Category category = Category.builder().id(CATEGORY_ID).name("Cat").build();

        when(productRepository.existByName("Updated")).thenReturn(false);
        when(productDtoMapper.toProduct(request)).thenReturn(product);
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(productDtoMapper.toResponseProductDto(product)).thenReturn(
                new ResponseProductDto(PRODUCT_ID.toString(), "Updated", "desc", 5, 50.0, Set.of())
        );

        ResponseProductDto result = productService.updateProduct(request);

        assertEquals("Updated", result.name());
        verify(productRepository).update(product);
        verify(productRepository).addCategory(product.getId(), category);
    }

    @Test
    @DisplayName("Should delete product successfully")
    void testDeleteProduct() {
        Product product = Product.builder().id(PRODUCT_ID).name("Del").build();
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        productService.deleteProduct(PRODUCT_ID);
        verify(productRepository).delete(PRODUCT_ID);
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when deleting unknown id")
    void testDeleteProductNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(PRODUCT_ID));
        verify(productRepository).findById(PRODUCT_ID);
        verifyNoMoreInteractions(productRepository);
    }
}
