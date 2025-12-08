package com.edu.web.spacecatsmarket.service.impl;

import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.TopProductProjection;
import com.edu.web.spacecatsmarket.service.ProductCatalogService;
import com.edu.web.spacecatsmarket.service.exception.ProductNotFoundException;
import com.edu.web.spacecatsmarket.service.mapper.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public ResponseProductDto getById(UUID id) {
        return productRepository.findById(id)
                .map(productDtoMapper::toResponseProductDto)
                .orElseThrow(() -> {
                    log.info("Product with id {} not found", id);
                    return new ProductNotFoundException("Product with id " + id + " not found");
                });
    }

    @Override
    public List<ResponseProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productDtoMapper::toResponseProductDto)
                .toList();
    }

    public List<TopProductProjection> getTopSellingProducts() {
        return productRepository.findTopSellingProducts();
    }
}