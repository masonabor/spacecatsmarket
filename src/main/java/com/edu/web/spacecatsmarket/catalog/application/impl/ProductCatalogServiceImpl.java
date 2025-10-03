package com.edu.web.spacecatsmarket.catalog.application.impl;

import com.edu.web.spacecatsmarket.catalog.application.ProductCatalogService;
import com.edu.web.spacecatsmarket.catalog.application.mapper.ProductDtoMapper;
import com.edu.web.spacecatsmarket.catalog.domain.Product;
import com.edu.web.spacecatsmarket.catalog.domain.ProductId;
import com.edu.web.spacecatsmarket.catalog.domain.ProductRepository;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.catalog.application.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper mapper;

    public ResponseProductDto getById(UUID id) {
        return mapper.toResponseDto(productRepository.findById(new ProductId(id)).orElseThrow(() -> {
            log.info("Product with id {} not found", id);
            return new ProductNotFoundException("Product with id " + id + " not found");
        }));
    }

    public List<ResponseProductDto> getAll() {
        List<Product> products = Optional.of(productRepository.findAll()).orElseThrow(() -> {
            log.info("All products not found");
            return new ProductNotFoundException("all products not found");
        });

        return products.stream()
                .map(mapper::toResponseDto)
                .toList();
    }

//    public List<Product> getAllByCategoryName(String categoryName) {
//        CategoryName category = new CategoryName(categoryName);
//
//        return productRepository.findAll().stream()
//                .filter(product -> product.getCategories().contains(category))
//                .toList();
//    }
}
