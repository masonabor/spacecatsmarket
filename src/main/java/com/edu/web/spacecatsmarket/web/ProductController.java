package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.catalog.application.ProductCatalogService;
import com.edu.web.spacecatsmarket.catalog.application.ProductService;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.catalog.domain.*;
import com.edu.web.spacecatsmarket.dto.product.RequestProductDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.exceptions.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<ResponseProductDto> getById(@PathVariable String id) {
        ResponseProductDto response = Optional.of(productCatalogService.getById(UUID.fromString(id)))
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
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
    public void delete(@PathVariable String id) {
        productService.deleteProduct(UUID.fromString(id));
    }
}
