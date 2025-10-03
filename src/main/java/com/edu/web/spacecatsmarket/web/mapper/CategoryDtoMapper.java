package com.edu.web.spacecatsmarket.web.mapper;

import com.edu.web.spacecatsmarket.catalog.application.dto.CategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.RequestCategoryDto;
import com.edu.web.spacecatsmarket.dto.category.ResponseCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        implementationName = "webCategoryDtoMapper",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryDtoMapper {

    CreateCategoryDto toCreate(RequestCategoryDto requestCategoryDto);
    RequestCategoryDto toRequest(CategoryDto categoryDto);
    ResponseCategoryDto toResponse(CategoryDto categoryDto);
}
