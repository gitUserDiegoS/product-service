package com.ecommerce.productservice.domain.usecase.product;


import com.ecommerce.productservice.domain.bussinesexception.ExceptionMessages;
import com.ecommerce.productservice.domain.model.passwordencoder.gateway.PasswordEncoderRepository;
import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.model.product.gateway.ProductRepository;


import com.ecommerce.productservice.domain.usecase.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * UseCase for handling user operations
 */
@Service
@RequiredArgsConstructor
public class ProductUseCase implements IproductUseCase {

    private final ProductRepository productRepository;

    private final PasswordEncoderRepository passwordEncoder;


    /**
     * Save a new {@link Product}
     *
     * @param product the user that would be created
     * @return user created
     */
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.update(product);
    }

    /**
     * Delete product by id
     *
     * @param id the product id to delete
     */
    @Override
    public void deleteProduct(Long id) {
        findProductById(id);
        productRepository.delete(id);
    }

    /**
     * Find products by id
     *
     * @param id the id to search
     * @return {@link Product} with the data of the product
     */
    @Override
    public Product findProductById(Long id) {
        Optional<Product> productById = productRepository.findById(id);

        if (productById.isEmpty()) {
            throw new ProductNotFoundException(ExceptionMessages.PRODUCT_NOT_FOUND_EXCEPTION);
        }

        return productById.get();
    }

    /**
     * Search products with filter and Pageable
     *
     * @param name     the product's name
     * @param minPrice search by min price
     * @param maxPrice search by max price
     * @param pageable add pageable function
     * @return {@link Product} with the product's data
     */
    @Override
    public Page<Product> searchProduct(String name, Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.search(name, minPrice, maxPrice, pageable);
    }
}
