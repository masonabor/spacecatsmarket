package com.edu.web.spacecatsmarket.service.impl;

import com.edu.web.spacecatsmarket.service.ProductCatalogService;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public ResponseProductDto getById(UUID id) {
        return productDtoMapper.toResponseProductDto(productRepository.findById(id).orElseThrow(() -> {
            log.info("Product with id {} not found", id);
            return new ProductNotFoundException("Product with id " + id + " not found");
        }));
    }

    public List<ResponseProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productDtoMapper::toResponseProductDto)
                .toList();
    }
}
