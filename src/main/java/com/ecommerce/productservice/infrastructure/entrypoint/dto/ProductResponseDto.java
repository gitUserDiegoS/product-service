package com.ecommerce.productservice.infrastructure.entrypoint.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response for a user created")
public class ProductResponseDto {

    @Schema(description = "id of the product", example = "21")
    private Long id;

    @Schema(description = "id for a user created", example = "21")
    private String sku;

    @Schema(description = "id for a user created", example = "21")
    private String name;

    @Schema(description = "id for a user created", example = "21")
    private String description;

    @Schema(description = "id for a user created", example = "21")
    private BigDecimal unitPrice;

    private boolean active;

    private int unitsInStock;

    private Date dateCreated;


    private Date lastUpdated;

}
