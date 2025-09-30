package com.edu.web.spacecatsmarket.catalog.application.mapper;

import com.edu.web.spacecatsmarket.catalog.application.dto.CreateProductDto;
import com.edu.web.spacecatsmarket.catalog.application.dto.UpdateProductDto;
import com.edu.web.spacecatsmarket.catalog.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProductDtoMapper {

    @Mapping(target = "id", expression = "java(ProductId.newId())")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", source = "categories")
    Product toProduct(CreateProductDto createProductDto);

    @Mapping(target = "id", expression = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", source = "categories")
    Product toProduct(UpdateProductDto updateProductDto);

    default ProductId mapToProductId(String id) {
        return id == null ? null : new ProductId(UUID.fromString(id));
    }

    // назва допоміжного методу має бути за таким прикладом: mapTo<цільовий об'єкт> (тут цільовий об'єкт - це ProductName в Product)
    default ProductName mapToProductName(String name) {
        return name == null ? null : new ProductName(name);
    }

    default ProductAmount mapToProductAmount(Integer amount) {
        return amount == null ? null : new ProductAmount(amount);
    }

    default Price mapToPrice(Double price) {
        return price == null ? null : new Price(price);
    }

//    default Set<Category> mapToCategories(Set<String> categories) {
//        if (categories == null) {
//            return null;
//        }
//
//        return categories.stream()
//                .filter(Objects::nonNull)
//                .map(category -> new Category(category))
//                .collect(Collectors.toSet());
//    }
}
