package com.edu.web.spacecatsmarket.catalog.application;

import com.edu.web.spacecatsmarket.dto.product.ResponseProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductCatalogService {

    ResponseProductDto getById(UUID id);
    List<ResponseProductDto> getAll();
}
