package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.service.ProductCatalogService;
import com.edu.web.spacecatsmarket.service.ProductService;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductCatalogService productCatalogService;
    private final ProductDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<ResponseProductDto>> getAll() {
        return ResponseEntity.ok(productCatalogService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> getById(@PathVariable UUID id) { // автоматичний парсинг через ConversionService (працює з багатьма типами та dto)
        ResponseProductDto response = productCatalogService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseProductDto> createProduct(@Valid @RequestBody CreateProductRequestDto createProductRequestDto) {
        ResponseProductDto response = productService.createProduct(createProductRequestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(@PathVariable String id, @Valid @RequestBody UpdateProductRequestDto updateProductRequestDto) {
        if (!id.equals(updateProductRequestDto.id())) {
            return ResponseEntity.badRequest().build();
        }
        ResponseProductDto response = productService.updateProduct(updateProductRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(UUID.fromString(id));
        return ResponseEntity.noContent().build(); // можна кидати таке при видаленні
    }
}
