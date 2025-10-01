package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.dto.product.RequestProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring",
        implementationName = "webProductDtoMapper",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductDtoMapper {

//    ResponseProductDto toResponseDto(CreateProductDto createProductDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "categories", ignore = true)
    UpdateProductDto toUpdateDto(String id, RequestProductDto requestProductDto);
    CreateProductDto toCreateDto(RequestProductDto requestProductDto);

    default UUID map(String id) {
        return UUID.fromString(id);
    }
}