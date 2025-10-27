package com.ecommerce.productservice.domain.model.productcategory;

import com.ecommerce.productservice.domain.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    private Long id;

    private String categoryName;

    private List<Product> products;
}
