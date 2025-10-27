package com.ecommerce.productservice.infrastructure.entrypoint.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "Response to return exceptions")
public class ErrorResponseDto {

    @Schema(description = "Code Exception", example = "EMAIL_ALREADY_REGISTERED")
    private String code;

    @Schema(description = "message Exception", example = "Email null is already registered")
    private String message;

    @Schema(description = "localTime exception", example = "2025-10-25T23:33:11.472532300")
    private String time;
}
