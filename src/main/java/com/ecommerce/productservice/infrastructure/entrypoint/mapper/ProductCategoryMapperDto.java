package com.ecommerce.productservice.infrastructure.entrypoint.mapper;

import com.ecommerce.productservice.domain.model.productcategory.ProductCategory;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.ProductCategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface ProductCategoryMapperDto {

    ProductCategory toModel(ProductCategoryDto dto);
}

