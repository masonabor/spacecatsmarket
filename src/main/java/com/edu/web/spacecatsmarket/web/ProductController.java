package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.catalog.domain.*;
import com.edu.web.spacecatsmarket.dto.product.RequestProductDto;
import com.edu.web.spacecatsmarket.exceptions.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;
    private final ProductDtoMapper mapper;

    @GetMapping
    public List<RequestProductDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public RequestProductDto getById(@PathVariable UUID id) {
        Product product = repository.findById(new ProductId(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return mapper.toDto(product);
    }

    @PostMapping("create")
    public RequestProductDto create(@Valid @RequestBody RequestProductDto dto) {
        Product product = mapper.toDomain(dto);
        repository.save(product);
        return mapper.toDto(product);
    }

    @PutMapping("/{id}")
    public RequestProductDto update(@PathVariable UUID id, @Valid @RequestBody RequestProductDto dto) {
        Product product = repository.findById(new ProductId(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        product.rename(dto.name());
        product.resetDescription(dto.description());
        product.changePrice(dto.price());
        product.addToAmount(dto.amount() - product.getAmount());

        repository.save(product);
        return mapper.toDto(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Product product = repository.findById(new ProductId(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        repository.delete(product);
    }
}
