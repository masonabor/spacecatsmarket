package com.edu.web.spacecatsmarket.web;

import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.domain.*;
import com.edu.web.spacecatsmarket.dto.product.RequestProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    @Mapping(target = "name", expression = "java(product.getName().name())")
    @Mapping(target = "price", expression = "java(product.getPrice().price())")
    RequestProductDto toDto(Product product);

    @Mapping(target = "id", expression = "java(ProductId.newId())")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    CreateProductDto toCreateDto(RequestProductDto dto);

    // TODO ResponseProductDto

    default Product toDomain(RequestProductDto dto) {
        return Product.builder()
                .id(ProductId.newId())
                .name(new ProductName(dto.name()))
                .description(dto.description())
                .amount(new ProductAmount(dto.amount()))
                .price(new Price(dto.price()))
                .categories(new HashSet<>())
                .build();
    }
}