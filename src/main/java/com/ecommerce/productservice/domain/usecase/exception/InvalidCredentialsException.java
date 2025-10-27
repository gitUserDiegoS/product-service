package com.ecommerce.productservice.domain.usecase.exception;


import com.ecommerce.productservice.domain.bussinesexception.BusinessException;
import com.ecommerce.productservice.domain.bussinesexception.ErrorCodes;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED);
    }
}
