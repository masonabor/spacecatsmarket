package com.edu.web.spacecatsmarket.service.impl;

import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import com.edu.web.spacecatsmarket.service.CategoryService;
import com.edu.web.spacecatsmarket.service.exception.CategoryAlreadyExistException;
import com.edu.web.spacecatsmarket.service.exception.CategoryNotFoundException;
import com.edu.web.spacecatsmarket.service.mapper.CategoryDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryDtoMapper categoryMapper;

    @Override
    @Transactional
    public ResponseCategoryDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        if (categoryRepository.existsByName(createCategoryRequestDto.name())) {
            throw new CategoryAlreadyExistException("Category with name " + createCategoryRequestDto.name() + " already exists");
        }

        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(createCategoryRequestDto);

        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return categoryMapper.toResponseCategoryDto(savedCategory);
    }

    @Override
    @Transactional
    public ResponseCategoryDto updateCategory(UpdateCategoryRequestDto updateCategoryRequestDto) {
        UUID id = UUID.fromString(updateCategoryRequestDto.id());

        CategoryEntity existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));

        if (!existingCategory.getName().equals(updateCategoryRequestDto.name())
                && categoryRepository.existsByName(updateCategoryRequestDto.name())) {
            throw new CategoryAlreadyExistException("Category with name " + updateCategoryRequestDto.name() + " already exists");
        }

        existingCategory.setName(updateCategoryRequestDto.name());

        return categoryMapper.toResponseCategoryDto(categoryRepository.save(existingCategory));
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
    @Transactional
    public void delete(UUID id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));

        List<ProductEntity> products = productRepository.findByCategoriesContaining(category);

        for (ProductEntity product : products) {
            product.getCategories().remove(category);
        }

        productRepository.saveAll(products);

        categoryRepository.delete(category);
    }
}