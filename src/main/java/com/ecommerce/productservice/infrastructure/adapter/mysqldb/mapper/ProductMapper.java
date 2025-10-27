package com.ecommerce.productservice.infrastructure.adapter.mysqldb.mapper;


import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.infrastructure.adapter.mysqldb.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category.products", ignore = true)
    ProductEntity toEntity(Product product);

    @Mapping(target = "category.products", ignore = true)
    Product toDomain(ProductEntity entity);

    List<Product> toDomainList(List<ProductEntity> entities);
}
