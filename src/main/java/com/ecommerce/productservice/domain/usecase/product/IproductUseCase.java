package com.ecommerce.productservice.domain.usecase.product;


import com.ecommerce.productservice.domain.model.product.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IproductUseCase {

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    Product findProductById(Long id);

    Page<Product> searchProduct(String name, Double minPrice, Double maxPrice, Pageable pageable);
}
