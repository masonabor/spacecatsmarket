package com.edu.web.spacecatsmarket.catalog.application.mapper;

import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.catalog.domain.*;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
@Named("applicationProductDtoMapper")
public interface ProductDtoMapper {

    @Mapping(target = "id", expression = "java(generateId())")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", ignore = true)
    Product toDomain(CreateProductDto dto);

    @Mapping(target = "id", expression = "java(mapToProductId(dto.id()))")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", ignore = true)
    Product toDomain(UpdateProductDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", source = "categories")
    ResponseProductDto toResponseDto(Product product);

    default ProductId generateId() {
        return ProductId.newId();
    }

    default ProductId mapToProductId(String id) {
        return new ProductId(UUID.fromString(id));
    }

    default ProductName map(String name) {
        return new ProductName(name);
    }

    default ProductAmount map(Integer amount) {
        return new ProductAmount(amount);
    }

    default Price map(Double price) {
        return new Price(price);
    }

    default String map(ProductName name) {
        return name.name();
    }

    default String map(ProductAmount amount) {
        return String.valueOf(amount.amount());
    }

    default Double map(Price price) {
        return price.price();
    }

    default UUID map(ProductId id) {
        return id.id();
    }

    default Set<String> mapToName(Set<Category> categories) {
        return categories.stream()
                .map(category -> category.getName().name())
                .collect(Collectors.toSet());
    }
}
