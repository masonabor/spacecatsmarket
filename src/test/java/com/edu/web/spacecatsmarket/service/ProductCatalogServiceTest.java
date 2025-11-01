package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.service.impl.ProductCatalogServiceImpl;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ProductCatalogServiceImpl.class})
@Import(ProductDtoMapper.class)
@DisplayName("ProductCatalog Service Tests")
public class ProductCatalogServiceTest {

    //mock data
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final Product PRODUCT = Product.builder().id(PRODUCT_ID).name("Product1").build();

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private ProductCatalogServiceImpl productCatalogService;

    @Test
    @DisplayName("Should get product by id")
    void testGetById() {
        ResponseProductDto dto = new ResponseProductDto(PRODUCT_ID.toString(), "Product1", null, null, null, Set.of());
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(PRODUCT));
        when(productDtoMapper.toResponseProductDto(PRODUCT)).thenReturn(dto);

        ResponseProductDto result = productCatalogService.getById(PRODUCT_ID);

        assertNotNull(result);
        assertEquals(PRODUCT_ID.toString(), result.id());
        verify(productRepository).findById(PRODUCT_ID);
        verify(productDtoMapper).toResponseProductDto(PRODUCT);
    }

    @Test
    @DisplayName("Should throw when product not found by id")
    void testGetByIdNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productCatalogService.getById(PRODUCT_ID));

        verify(productRepository).findById(PRODUCT_ID);
        verifyNoInteractions(productDtoMapper);
    }

    @Test
    @DisplayName("Should get all products")
    void testGetAll() {
        Product p2 = Product.builder().id(UUID.randomUUID()).name("Product2").build();
        List<Product> products = List.of(PRODUCT, p2);
        when(productRepository.findAll()).thenReturn(products);

        ResponseProductDto dto1 = new ResponseProductDto(PRODUCT_ID.toString(), "Product1", null, null, null, Set.of());
        ResponseProductDto dto2 = new ResponseProductDto(p2.getId().toString(), "Product2", null, null, null, Set.of());

        when(productDtoMapper.toResponseProductDto(PRODUCT)).thenReturn(dto1);
        when(productDtoMapper.toResponseProductDto(p2)).thenReturn(dto2);

        List<ResponseProductDto> result = productCatalogService.getAll();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
        verify(productDtoMapper).toResponseProductDto(PRODUCT);
        verify(productDtoMapper).toResponseProductDto(p2);
    }
}
