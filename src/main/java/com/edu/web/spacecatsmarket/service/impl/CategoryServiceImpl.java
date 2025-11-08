package com.edu.web.spacecatsmarket.service.impl;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.service.CategoryService;
import com.edu.web.spacecatsmarket.service.exception.CategoryAlreadyExistException;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.mapper.CategoryDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryDtoMapper categoryMapper;

    @Override
    public ResponseCategoryDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        if (categoryRepository.existsByName(createCategoryRequestDto.name())) {
            throw new CategoryAlreadyExistException("Category with name " + createCategoryRequestDto.name() + " already exists");
        }
        Category category = categoryMapper.toCategory(createCategoryRequestDto);
        category.generateId();
        categoryRepository.save(category);
        return categoryMapper.toResponseCategoryDto(category);
    }

    @Override
    public ResponseCategoryDto updateCategory(UpdateCategoryRequestDto updateCategoryRequestDto) {
        Category category = categoryMapper.toCategory(updateCategoryRequestDto);

        Category previousCategory = categoryRepository.findById(
                        UUID.fromString(updateCategoryRequestDto.id()))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + updateCategoryRequestDto.id()));

        HashMap<UUID, Product> updatedProducts = productRepository.findAllByCategory(previousCategory)
                .stream()
                .peek(product -> {
                    productRepository.removeCategory(product.getId(), previousCategory);
                    productRepository.addCategory(product.getId(), category);
                })
                .collect(Collectors.toMap(
                        Product::getId,
                        Function.identity(),
                        (existing, ignoredValue) -> existing,
                        HashMap::new
                ));

        productRepository.saveAll(updatedProducts);
        categoryRepository.save(category);
        return categoryMapper.toResponseCategoryDto(category);
    }

    @Override
    public ResponseCategoryDto getById(UUID id) {
        return categoryMapper.toResponseCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id)));
    }

    @Override
    public List<ResponseCategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponseCategoryDto)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category != null) {
            HashMap<UUID, Product> updatedProducts = productRepository.findAllByCategory(category)
                    .stream()
                    .peek(product -> productRepository.removeCategory(product.getId(), category))
                    .collect(Collectors.toMap(
                            Product::getId,
                            Function.identity(),
                            (existing, ignoredValue) -> existing,
                            HashMap::new
                    ));

            productRepository.saveAll(updatedProducts);
            categoryRepository.delete(id);
        }
    }
}
