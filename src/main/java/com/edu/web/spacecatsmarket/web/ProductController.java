package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.catalog.application.ProductCatalogService;
import com.edu.web.spacecatsmarket.catalog.application.ProductService;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.dto.product.RequestProductDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.web.mapper.ProductDtoMapper;
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
    public ResponseEntity<ResponseProductDto> create(@Valid @RequestBody RequestProductDto dto) {
        ResponseProductDto response = productService.createProduct(mapper.toCreateDto(dto));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDto> update(@PathVariable String id, @Valid @RequestBody RequestProductDto dto) {
        UpdateProductDto updateProductDto = mapper.toUpdateDto(id, dto);
        ResponseProductDto response = productService.updateProduct(updateProductDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.deleteProduct(UUID.fromString(id));
        return ResponseEntity.noContent().build(); // можна кидати таке при видаленні
    }
}
