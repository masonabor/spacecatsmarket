package com.edu.web.spacecatsmarket.web.mapper;

import com.edu.web.spacecatsmarket.catalog.application.dto.CategoryDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.CreateCategoryDto;
import com.edu.web.spacecatsmarket.web.dto.category.RequestCategoryDto;
import com.edu.web.spacecatsmarket.web.dto.category.ResponseCategoryDto;
import com.edu.web.spacecatsmarket.web.dto.category.UpdateCategoryRequestDto;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring",
        implementationName = "webCategoryDtoMapper",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryDtoMapper {

    CreateCategoryDto toCreate(RequestCategoryDto requestCategoryDto);
    RequestCategoryDto toRequest(CategoryDto categoryDto);
    ResponseCategoryDto toResponse(CategoryDto categoryDto);
    UpdateCategoryRequestDto toUpdate(UUID id, @Valid RequestCategoryDto request);
}
