package com.ecommerce.productservice.infrastructure.entrypoint;


import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.usecase.product.IproductUseCase;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.CreateProductDto;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.ProductResponseDto;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.UpdateProductDto;
import com.ecommerce.productservice.infrastructure.entrypoint.mapper.ProductMapperDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Rest controller for managing products, used for CRUD operations
 */
@Tag(name = "Users", description = "Operations related to users")
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {


    private final IproductUseCase useCase;
    private final ProductMapperDto mapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody CreateProductDto dto) {
        Product model = mapper.toModel(dto);
        Product product = useCase.saveProduct(model);
        return ResponseEntity.ok(mapper.toResponse(product));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
        Product productById = useCase.findProductById(id);
        return ResponseEntity.ok(mapper.toResponse(productById));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ProductResponseDto> update(@RequestBody UpdateProductDto dto) {
        Product updated = useCase.updateProduct(mapper.toModel(dto));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Pageable pageable) {
        return ResponseEntity.ok(useCase.searchProduct(name, minPrice, maxPrice, pageable)
                .map(mapper::toResponse));
    }
}
