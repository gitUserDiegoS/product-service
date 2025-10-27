package com.ecommerce.productservice.domain.usecase.exception;


import com.ecommerce.productservice.domain.bussinesexception.BusinessException;
import com.ecommerce.productservice.domain.bussinesexception.ErrorCodes;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(String message) {
        super(message, ErrorCodes.NOT_FOUND);
    }
}
