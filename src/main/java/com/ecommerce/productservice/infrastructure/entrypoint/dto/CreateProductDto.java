package com.ecommerce.productservice.infrastructure.entrypoint.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto response for products")
public class CreateProductDto {

    @Schema(description = "User documentId", example = "1234567")
    private Long id;

    private String sku;

    private String name;

    private String description;

    private BigDecimal unitPrice;

    private boolean active;

    private int unitsInStock;

    private Date dateCreated;

    private Date lastUpdated;

    private ProductCategoryDto category;

}
