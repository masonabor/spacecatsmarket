package com.edu.web.spacecatsmarket.catalog.application.mapper;

import com.edu.web.spacecatsmarket.catalog.application.dto.CategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.catalog.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface CategoryDtoMapper {

    @Mapping(target = "id", expression = "java(CategoryId.newId())")
    @Mapping(target = "name", source = "name")
    Category toDomain(CreateCategoryDto dto);

    @Mapping(target = "id", expression = "java(category.getId().toString())")
    @Mapping(target = "name", expression = "java(category.getName().name())")
    CategoryDto toDto(Category category);

    // допоміжні методи (MapStruct використовує ці default методи якщо треба)
    default CategoryName mapToCategoryName(String name) {
        return name == null ? null : new CategoryName(name);
    }
}
