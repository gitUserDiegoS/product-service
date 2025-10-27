package com.ecommerce.productservice.infrastructure.adapter.mysqldb;


import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.model.product.gateway.ProductRepository;

import com.ecommerce.productservice.infrastructure.adapter.mysqldb.entity.ProductEntity;
import com.ecommerce.productservice.infrastructure.adapter.mysqldb.mapper.ProductMapper;

import com.ecommerce.productservice.infrastructure.adapter.mysqldb.repository.ProductRepositoryJpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Optional;

/**
 * Adapter MySql that supports queries to the database
 */
@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductRepositoryJpa jpaRepository;

    private final ProductMapper productMapper;


    /**
     * Create a {@link Product}
     *
     * @param product used to search data
     * @return product's data
     */
    @Override
    public Product save(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        return productMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Product update(Product product) {
        ProductEntity entity = productMapper.toEntity(product);

        Optional<Product> productById = findById(product.getId());

        productById.ifPresent(value -> {
            entity.getCategory().setId(value.getCategory().getId());
            entity.setDateCreated(value.getDateCreated());
            entity.setLastUpdated(new Date(System.currentTimeMillis()));
        });


        return productMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {

        Optional<ProductEntity> byId = jpaRepository.findById(id);

        if (byId.isPresent()) {
            ProductEntity productEntity = byId.get();
            return Optional.of(productMapper.toDomain(productEntity));
        }

        return Optional.empty();
    }

    @Override
    public Page<Product> search(String name, Double minPrice, Double maxPrice, Pageable pageable) {
        return jpaRepository.search(name, minPrice, maxPrice, pageable).map(productMapper::toDomain);
    }
}
