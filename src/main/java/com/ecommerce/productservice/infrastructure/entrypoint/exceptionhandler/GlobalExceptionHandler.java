package com.ecommerce.productservice.infrastructure.entrypoint.exceptionhandler;


import com.ecommerce.productservice.domain.bussinesexception.BusinessException;
import com.ecommerce.productservice.infrastructure.entrypoint.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import javax.naming.AuthenticationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception ex) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ErrorType.INTERNAL_SERVER_ERROR.name(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleExceptionAcceddDenied(
            AccessDeniedException ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ErrorType.FORBIDDEN.name(),
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleExceptionNotAuthenticated(
            AuthenticationException ex) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ErrorType.UNAUTHORIZED.name(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(
            BusinessException ex) {
        log.error("Diego-->Captured BusinessException: {}", ex.getMessage());
        HttpStatus status = ErrorType.fromCode(ex.getErrorCode());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getErrorCode(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleSqlIntegration(
            SQLIntegrityConstraintViolationException ex) {


        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ErrorType.FORBIDDEN.name(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }


}



