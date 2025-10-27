package com.ecommerce.productservice.infrastructure.entrypoint.mapper;


import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.CreateProductDto;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.ProductResponseDto;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.UpdateProductDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductCategoryMapperDto.class},
        builder = @org.mapstruct.Builder(disableBuilder = true))
public interface ProductMapperDto {

    @Mapping(target = "category.products", ignore = true)
    Product toModel(CreateProductDto dto);

    @Mapping(target = "category.products", ignore = true)
    Product toModel(UpdateProductDto dto);

    ProductResponseDto toResponse(Product product);

}
