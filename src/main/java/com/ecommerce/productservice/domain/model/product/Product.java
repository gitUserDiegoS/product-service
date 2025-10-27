package com.ecommerce.productservice.domain.model.product;

import com.ecommerce.productservice.domain.model.productcategory.ProductCategory;
import com.ecommerce.productservice.infrastructure.adapter.mysqldb.entity.ProductCategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;

    private String sku;

    private String name;

    private String description;

    private BigDecimal unitPrice;

    private boolean active;

    private int unitsInStock;

    private Date dateCreated;

    private Date lastUpdated;

    private ProductCategory category;

}
