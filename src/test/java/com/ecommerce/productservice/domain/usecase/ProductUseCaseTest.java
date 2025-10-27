package com.ecommerce.productservice.domain.usecase;

import com.ecommerce.productservice.domain.bussinesexception.ExceptionMessages;
import com.ecommerce.productservice.domain.model.passwordencoder.gateway.PasswordEncoderRepository;
import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.model.product.gateway.ProductRepository;
import com.ecommerce.productservice.domain.usecase.exception.ProductNotFoundException;
import com.ecommerce.productservice.domain.usecase.product.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PasswordEncoderRepository passwordEncoderRepository;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Laptop");
        sampleProduct.setDescription("Gaming laptop");
        sampleProduct.setUnitPrice(BigDecimal.valueOf(1200));
        sampleProduct.setActive(true);
    }


    @Test
    void saveProduct_shouldReturnSavedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product saved = productUseCase.saveProduct(sampleProduct);

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Laptop");
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        when(productRepository.update(any(Product.class))).thenReturn(sampleProduct);

        Product updated = productUseCase.updateProduct(sampleProduct);

        assertThat(updated.getName()).isEqualTo("Laptop");
        verify(productRepository, times(1)).update(any(Product.class));
    }


    @Test
    void findProductById_shouldReturnProduct_whenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        Product found = productUseCase.findProductById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(productRepository, times(1)).findById(1L);
    }


    @Test
    void findProductById_shouldThrowException_whenNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productUseCase.findProductById(1L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage(ExceptionMessages.PRODUCT_NOT_FOUND_EXCEPTION);

        verify(productRepository, times(1)).findById(1L);
    }


    @Test
    void deleteProduct_shouldCallRepositoryDelete() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        productUseCase.deleteProduct(1L);

        verify(productRepository, times(1)).delete(1L);
    }


    @Test
    void searchProduct_shouldReturnPagedResults() {
        Page<Product> page = new PageImpl<>(List.of(sampleProduct));
        when(productRepository.search(anyString(), anyDouble(), anyDouble(), any(PageRequest.class)))
                .thenReturn(page);

        Page<Product> result = productUseCase.searchProduct("Laptop", 1000.0, 1500.0, PageRequest.of(0, 5));

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Laptop");
    }
}
