package com.edu.web.spacecatsmarket.service.mapper;

import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.dto.category.CreateCategoryRequestDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.UpdateCategoryRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Primary;

import java.util.UUID;

@Mapper(componentModel = "spring",
        implementationName = "webCategoryDtoMapper",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CategoryDtoMapper {

    @Mapping(target = "id", ignore = true)
    CategoryEntity toCategoryEntity(CreateCategoryRequestDto createCategoryRequestDto);

    @Mapping(target = "id", source = "id", qualifiedByName = "toStringCategoryId")
    @Mapping(target = "name", source = "name")
    ResponseCategoryDto toResponseCategoryDto(CategoryEntity categoryEntity);

    @Mapping(target = "id", source = "id", qualifiedByName = "toStringCategoryId")
    @Mapping(target = "name", source = "name")
    ResponseCategoryDto toResponseCategoryDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Category toCategory(CreateCategoryRequestDto categoryDto);

    @Mapping(target = "id", source = "id", qualifiedByName = "toCategoryId")
    @Mapping(target = "name", source = "name")
    Category toCategory(UpdateCategoryRequestDto categoryDto);

    @Named(value = "toCategoryId")
    default UUID toCategoryId(String categoryId) {
        return UUID.fromString(categoryId);
    }

    @Named(value = "toStringCategoryId")
    default String toStringCategoryId(UUID categoryId) {
        return categoryId.toString();
    }
}
