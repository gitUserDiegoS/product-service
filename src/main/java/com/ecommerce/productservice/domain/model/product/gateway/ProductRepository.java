package com.ecommerce.productservice.domain.model.product.gateway;


import com.ecommerce.productservice.domain.model.product.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

/**
 * Defines a contract to perform operations over products
 */
public interface ProductRepository {

    Product save(Product user);

    Product update(Product user);

    void delete(Long id);

    Optional<Product> findById(Long id);

    Page<Product> search(String name, Double minPrice, Double maxPrice, Pageable pageable);

}
