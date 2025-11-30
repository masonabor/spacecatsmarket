package com.edu.web.spacecatsmarket.service.mapper;

import com.edu.web.spacecatsmarket.domain.catalog.Product;
import com.edu.web.spacecatsmarket.dto.product.CreateProductRequestDto;
import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;
import com.edu.web.spacecatsmarket.dto.product.UpdateProductRequestDto;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring",
        implementationName = "webProductDtoMapper",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {CategoryDtoMapper.class}
)
public interface ProductDtoMapper {

    @Mapping(target = "id", source = "id", qualifiedByName = "toStringProductId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", source = "categories")
    ResponseProductDto toResponseProductDto(ProductEntity productEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", ignore = true)
    ProductEntity toProductEntity(CreateProductRequestDto createProductRequestDto);

    @Mapping(target = "id", source = "id", qualifiedByName = "toStringProductId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", source = "categories")
    ResponseProductDto toResponseProductDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", ignore = true)
    Product toProduct(CreateProductRequestDto dto);

    @Mapping(target = "id", source = "id", qualifiedByName = "toProductId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", ignore = true)
    Product toProduct(UpdateProductRequestDto dto);

    @Named(value = "toProductId")
    default UUID toProductId(String productId) {
        return UUID.fromString(productId);
    }

    @Named(value = "toStringProductId")
    default String toStringProductId(UUID productId) {
        return productId.toString();
    }
}