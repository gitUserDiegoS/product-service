package com.ecommerce.productservice.domain.model.passwordencoder.gateway;


/**
 * Define contract to encode and decode a password
 */
public interface PasswordEncoderRepository {

    Boolean matches(String rawPassword, String encodedPassword);
}
