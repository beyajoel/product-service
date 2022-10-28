package com.bent.productservice.mapper;

import com.bent.productservice.dto.ProductRequest;
import com.bent.productservice.dto.ProductResponse;
import com.bent.productservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toResponse(Product product);

    Product fromResponse(ProductResponse response);

    ProductRequest toRequest(Product product);

    Product fromRequest(ProductRequest request);
}
