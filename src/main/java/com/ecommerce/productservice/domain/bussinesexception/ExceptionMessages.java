package com.ecommerce.productservice.domain.bussinesexception;

public final class ExceptionMessages {

    public static final String EMAIL_REGISTERED = "Email %s registered before";

    public static final String INVALID_TOKEN = "Invalid Token";

    public static final String PRODUCT_NOT_FOUND_EXCEPTION = "Product not found";

    public static final String INVALID_CREDENTIAL_EXCEPTION = "Please check the email or password, invalid credentials";


    private ExceptionMessages() {
        throw new IllegalStateException("Utility class");
    }

}
