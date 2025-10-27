package com.ecommerce.productservice.infrastructure.entrypoint.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Used for updating products")
public class UpdateProductDto {

    @Schema(description = "User documentId", example = "1234567")
    private Long id;

    private String sku;

    private String name;

    private String description;

    private BigDecimal unitPrice;

    private boolean active;

    private int unitsInStock;


}
